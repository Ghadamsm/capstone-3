package com.example.blooddonationsystem.Service;

import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.EmergencyPatient;
import com.example.blooddonationsystem.Model.Reservation;
import com.example.blooddonationsystem.Model.User;
import com.example.blooddonationsystem.Repository.EmergencyPatientRepository;
import com.example.blooddonationsystem.Repository.ReservationRepository;
import com.example.blooddonationsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final EmergencyPatientRepository emergencyPatientRepository;

    private final UserRepository userRepository;


    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public void updateReservation(Integer id, Reservation updatedReservation) {
        Reservation reservation = reservationRepository.findReservationById(id);

        if (reservation == null)
            throw new ApiException("reservation not found");

        reservation.setUsers(updatedReservation.getUsers());
        reservation.setEmergencyPatient(updatedReservation.getEmergencyPatient());
        reservation.setStatus(updatedReservation.getStatus());

        reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        Reservation r = reservationRepository.findReservationById(id);

        if (r == null) {
            throw new ApiException("not found");
        }
        reservationRepository.delete(r);
    }


    //    endpoint


    // user make a reservation to donate blood to an emergency patient
    public void addReservation(Integer user_id, Integer emergencyPatinet_id) {
        EmergencyPatient emergencyPatient = emergencyPatientRepository.findEmergencyPatientById(emergencyPatinet_id);
        User user = userRepository.findUserById(user_id);

        if(emergencyPatient == null)
            throw new ApiException("Emergency Patient not found");

        if(emergencyPatient.getBloodDonation() == 0)
            throw new ApiException("Cannot reserve for this patient");

        if(user == null)
            throw new ApiException("User not found");

        if(!checkBloodType(user, emergencyPatient))
            throw new ApiException("User blood type is not compatible with the emergency patient blood type");

        Reservation reservation = new Reservation();
        reservation.setEmergencyPatient(emergencyPatient);
        reservation.getUsers().add(user);
        reservation.setStatus("pending");

        reservationRepository.save(reservation);
    }

    // check if the user blood type can donate blood to the emergency patient
    public boolean checkBloodType(User user, EmergencyPatient emergencyPatient) {
        String userBloodType = user.getBloodType();
        String emergencyPatientBloodType = emergencyPatient.getBloodType();

        if (userBloodType.equals("O-")) {
            return true;

        } else if (userBloodType.equals("O+")) {
            return emergencyPatientBloodType.equals("O+") || emergencyPatientBloodType.equals("A+") || emergencyPatientBloodType.equals("B+") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("A-")) {
            return emergencyPatientBloodType.equals("A-") || emergencyPatientBloodType.equals("A+") || emergencyPatientBloodType.equals("AB-") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("A+")) {
            return emergencyPatientBloodType.equals("A+") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("B-")) {
            return emergencyPatientBloodType.equals("B-") || emergencyPatientBloodType.equals("B+") || emergencyPatientBloodType.equals("AB-") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("B+")) {
            return emergencyPatientBloodType.equals("B+") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("AB-")) {
            return emergencyPatientBloodType.equals("AB-") || emergencyPatientBloodType.equals("AB+");

        } else if (userBloodType.equals("AB+")) {
            return emergencyPatientBloodType.equals("AB+");
        }

        return false;
    }



    public String cancelReservation(Integer userId , Integer reservationId){
       Reservation reservation = reservationRepository.findReservationById(reservationId);

       if (reservation == null ){
           throw new ApiException("invalid reservation");
       }

       for(User user : reservation.getUsers()){
           if (user.getId().equals(userId)){
               reservation.setStatus("canceled");
               reservationRepository.save(reservation);
               return "reservation canceled";
           }
       }

       return "sorry we cannot find the reservation";
    }


}
