package com.example.blooddonationsystem.Service;

import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.Appointment;
import com.example.blooddonationsystem.Model.AvailableSlot;
import com.example.blooddonationsystem.Model.BloodBank;
import com.example.blooddonationsystem.Model.User;
import com.example.blooddonationsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final BloodBankRepository bloodBankRepository;
    private final VolunteeringRepository volunteeringRepository;
    private final AvailableSlotRepository availableSlotRepository;


    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public void updateAppointment(Integer appointment_id, Appointment appointment,Integer user_id) {
        Appointment existingAppointment = appointmentRepository.findAppointmentById(appointment_id);

        if (existingAppointment == null) {
            throw new ApiException("Appointment not found");
        }

        if (!existingAppointment.getUser().getId().equals(user_id)) {
            throw new ApiException("User is not authorized to update this appointment");
        }

        existingAppointment.setDateTime(appointment.getDateTime());
        existingAppointment.setStatus(appointment.getStatus());

        appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Integer appointment_id, Integer user_id) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointment_id);

        if (appointment == null) {
            throw new ApiException("Appointment not found");
        }

        if (!appointment.getUser().getId().equals(user_id)) {
            throw new ApiException("User is not authorized to delete this appointment");
        }

        appointmentRepository.delete(appointment);
    }

    //    endpoint


    // get user appointements
    public List<Appointment> getUserAppointments(Integer user_id) {
        return appointmentRepository.findAppointmentsByUserId(user_id);
    }

    // user can rate the appointment
    public void rateAppointment(Integer user_id, Integer appointment_id, Double rating) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointment_id);

        if (appointment == null)
            throw new ApiException("Appointment not found");

        if (!appointment.getUser().getId().equals(user_id))
            throw new ApiException("User is not authorized to rate this appointment");

        if(rating < 0 || rating > 5)
            throw new ApiException("Rating should be between 0 and 5");

        if(!appointment.getAttended().equalsIgnoreCase("attended"))
            throw new ApiException("User can only rate attended appointments");

        appointment.setRating(rating);

        // calculate new average rating for blood bank
        BloodBank bloodBank = appointment.getBloodBank();
        double averageRating = bloodBank.getAverageRating() == null ? 0 : bloodBank.getAverageRating();
        int ratingCount = bloodBank.getRatingCount() == null ? 0 : bloodBank.getRatingCount();

        double newAverageRating = (averageRating * ratingCount + rating) / (ratingCount + 1);
        bloodBank.setAverageRating(newAverageRating);
        bloodBank.setRatingCount(ratingCount + 1);

        appointmentRepository.save(appointment);
        bloodBankRepository.save(bloodBank);
    }

    // user can cancel appointment
    public void cancelAppointment(Integer user_id, Integer appointment_id) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointment_id);

        if (appointment == null)
            throw new ApiException("Appointment not found");

        if (!appointment.getUser().getId().equals(user_id))
            throw new ApiException("User is not authorized to cancel this appointment");

        if(appointment.getAttended().equalsIgnoreCase("Attended"))
            throw new ApiException("User can only cancel unattended appointments");

        appointment.setStatus("cancelled");
    }



    // get top 10 rated blood banks
    public List<BloodBank> getTopRatedBloodBanks() {
        return bloodBankRepository.findTop10ByOrderByAverageRatingDesc();
    }


    // book appointment
    public void bookAppointment(Appointment appointment, Integer user_id, Integer bloodbank_id) {
        User user = userRepository.findUserById(user_id);
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(bloodbank_id);

        if (user == null || bloodBank == null)
            throw new ApiException("User or BloodBank not found");

        List<LocalDateTime> availableSlots = getAvailableSlots(bloodbank_id);
        if (!availableSlots.contains(appointment.getDateTime())) {
            throw new ApiException("The chosen time slot is not available");
        } else if (bloodBank.getIsThere().equals(false)){
        throw new ApiException("BloodBank is not there");
    } else

        appointment.setUser(user);
        appointment.setBloodBank(bloodBank);
        appointment.setAttended("unattended");
        appointment.setStatus("scheduled");

        AvailableSlot bookedSlot = availableSlotRepository.findByBloodBankIdAndDateTime(bloodbank_id, appointment.getDateTime());
        if (bookedSlot != null) {
            availableSlotRepository.delete(bookedSlot);
        }

        appointmentRepository.save(appointment);
    }


    // get available slots for a blood bank
    public List<LocalDateTime> getAvailableSlots(Integer bloodbank_id) {
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(bloodbank_id);

        if (bloodBank == null)
            throw new ApiException("BloodBank not found");

        // Get all available slots for a blood bank
        List<AvailableSlot> availableSlots = availableSlotRepository.findAvailableSlotsByBloodBankId(bloodbank_id);

        // Get all appointments for a blood bank
        List<Appointment> appointments = appointmentRepository.findAppointmentsByBloodBankId(bloodbank_id);

        // Filter out the slots that are already booked
        List<LocalDateTime> availableTimes = availableSlots.stream()
                .map(AvailableSlot::getDateTime)
                .filter(slot -> appointments.stream().noneMatch(a -> a.getDateTime().equals(slot)))
                .collect(Collectors.toList());

        return availableTimes;
    }

}
