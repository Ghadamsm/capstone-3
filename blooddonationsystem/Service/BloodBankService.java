package com.example.blooddonationsystem.Service;

import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.*;
import com.example.blooddonationsystem.Model.BloodBank;
import com.example.blooddonationsystem.Repository.AppointmentRepository;
import com.example.blooddonationsystem.Repository.AvailableSlotRepository;
import com.example.blooddonationsystem.Repository.BloodBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodBankService {

    private final BloodBankRepository bloodBankRepository;
    private final AppointmentRepository appointmentRepository ;
    private final AvailableSlotRepository availableSlotRepository;
    public List<BloodBank> getAllBloodBanks() {
        return bloodBankRepository.findAll();
    }

    public void addBloodBank(BloodBank bloodBank) {
        bloodBank.setIsThere(Boolean.TRUE);
        bloodBank.setDate(LocalDate.now());
        bloodBankRepository.save(bloodBank);
    }

    public void updateBloodBank(Integer id, BloodBank updatedBloodBank) {
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(id);

        if (bloodBank == null)
            throw new ApiException("Blood bank not found");

        bloodBank.setName(updatedBloodBank.getName());
        bloodBank.setCity(updatedBloodBank.getCity());

        bloodBankRepository.save(bloodBank);
    }

    public void deleteBloodBank(Integer id) {
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(id);

        if (bloodBank == null)
            throw new ApiException("Blood bank not found");

        bloodBankRepository.delete(bloodBank);
    }

    // update available slot
    public void updateAvailableSlot(Integer bloodbank_id, Integer slot_id, AvailableSlot updatedSlot) {
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(bloodbank_id);
        AvailableSlot availableSlot = availableSlotRepository.findAvailableSlotById(slot_id);

        if (bloodBank == null || availableSlot == null)
            throw new ApiException("BloodBank or AvailableSlot not found");

        if (!availableSlot.getBloodBank().getId().equals(bloodbank_id))
            throw new ApiException("BloodBank is not authorized to update this slot");

        availableSlot.setDateTime(updatedSlot.getDateTime());

        availableSlotRepository.save(availableSlot);
    }




    //    endpoint

    public List<Appointment> getAllAppointments(String isAttended ) {

        return appointmentRepository.findAppointmentsByAttendedEqualsIgnoreCase(isAttended);
    }

    // blood bank can change user appointment status to attended
    public void attendAppointment(Integer bloodbank_id, Integer appointment_id) {
        Appointment appointment = appointmentRepository.findAppointmentById(appointment_id);

        if (appointment == null)
            throw new ApiException("Appointment not found");

        if (!appointment.getBloodBank().getId().equals(bloodbank_id))
            throw new ApiException("Blood bank is not authorized to change the status of this appointment");

        if(appointment.getStatus().equalsIgnoreCase("Attended"))
            throw new ApiException("Appointment is already attended");

        appointment.setAttended("attended");

        appointmentRepository.save(appointment);
    }



    // تغيير حالة البلود بانك اذا موجود بالخدمة ولا لا
    public String IsThereABloodBank(Integer bankId , Boolean isThere ){
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(bankId);

        if (bloodBank == null){
            throw new ApiException("Blood bank not found");
        } else if (isThere.equals(true)) {
            bloodBank.setDate(LocalDate.now());
            bloodBank.setIsThere(isThere);
            bloodBankRepository.save(bloodBank);
        } else {
            bloodBank.setIsThere(isThere);
            bloodBankRepository.save(bloodBank);
        }

        return "the blood bank presence status has been changed to " + isThere;
    }

    // add availble slot
    public void addAvailableSlot(Integer bloodbank_id, AvailableSlot availableSlot) {
        BloodBank bloodBank = bloodBankRepository.findBloodBankById(bloodbank_id);

        if (bloodBank == null)
            throw new ApiException("BloodBank not found");

        availableSlot.setBloodBank(bloodBank);

        availableSlotRepository.save(availableSlot);
    }
}
