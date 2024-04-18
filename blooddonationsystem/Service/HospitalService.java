package com.example.blooddonationsystem.Service;

import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.*;
import com.example.blooddonationsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final EmergencyPatientRepository emergencyPatientRepository;
    private final ReservationRepository resRepository ;
    private final HospitalVolunteerRepository hospitalVolunteerRepository ;
    private final VolunteeringRepository volunteeringRepository;
    private final VolunteeringRequestRepository volunteeringRequestRepository;
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public void addHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
    }

    public void updateHospital(Integer id, Hospital updatedHospital) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null)
            throw new ApiException("Hospital not found");

        hospital.setName(updatedHospital.getName());
        hospital.setCity(updatedHospital.getCity());

        hospitalRepository.save(hospital);
    }

    public void deleteHospital(Integer id) {
        Hospital hospital = hospitalRepository.findHospitalById(id);

        if (hospital == null)
            throw new ApiException("Hospital not found");

        hospitalRepository.delete(hospital);
    }


    //    endpoint



    // get emergency patients cases that are completed for a hospital
    public List<EmergencyPatient> getCompletedEmergencyPatients(Integer hospital_id) {
        return emergencyPatientRepository.findEmergencyPatientsByHospitalIdAndStatus(hospital_id, "completed");
    }

    // تغيير حالة احد الحالات الحرجة
    public void changeBloodDonationStatus(Integer hospitalId, Integer caseId, String status){
        Hospital hospital = hospitalRepository.findHospitalById(hospitalId);
        EmergencyPatient emergencyPatient = emergencyPatientRepository.findEmergencyPatientById(caseId);

        if(!status.equalsIgnoreCase("completed") && !status.equalsIgnoreCase("not completed"))
            throw new ApiException("Invalid status, status should be either completed or not completed");

        if (hospital == null || emergencyPatient == null){
            throw new ApiException("Hospital or emergency patient not found");
        }

        emergencyPatient.setStatus(status);

        emergencyPatientRepository.save(emergencyPatient);
    }


    //  قبول او رفض التطوع

    public String AcceptingOrRejectingVolunteering(Integer hospitalId, Integer hospitalVolunteerId , Integer volunteerId , String status ){
        Hospital hospital = hospitalRepository.findHospitalById(hospitalId);
        HospitalVolunteer hospitalVolunteer = hospitalVolunteerRepository.findHospitalVolunteerById(hospitalVolunteerId);
        Volunteering volunteering = volunteeringRepository.findVolunteeringById(volunteerId);

        if(!status.equalsIgnoreCase("Accepted") && !status.equalsIgnoreCase("Rejected"))
            throw new ApiException("Invalid status, status should be either Accepted or Rejected");

        if (hospital == null || hospitalVolunteer == null || volunteering == null){
            throw new ApiException("Hospital or volunteer not found");
        }

        VolunteeringRequest volunteeringRequest = volunteeringRequestRepository.findByVolunteeringIdAndHospitalVolunteerId(volunteerId, hospitalVolunteerId);

        if (volunteeringRequest == null){
            throw new ApiException("Volunteering request not found");
        }

        if (volunteeringRequest.getHospitalVolunteer().getNumberOfVolunteers() == 0){
            throw new ApiException("sorry , you cannot volunteer for this opportunity");
        }

        volunteeringRequest.setStatus(status);
        volunteeringRequest.getHospitalVolunteer().setNumberOfVolunteers(volunteeringRequest.getHospitalVolunteer().getNumberOfVolunteers() - 1);
        volunteeringRequestRepository.save(volunteeringRequest);


        return "status change to " + status;
    }


     //    قبول او رفض موعد التبرع للدم للحالة الطارئة

    public String AcceptingOrRejectingBloodDonation(Integer hospitalId, Integer reservationId , String status ){
        Hospital hospital = hospitalRepository.findHospitalById(hospitalId);
        Reservation reservation = resRepository.findReservationById(reservationId);

        if (hospital == null || reservation == null){
            throw new ApiException("Hospital or reservation not found");
        } else if (reservation.getEmergencyPatient().getHospital().getId().equals(hospitalId)) {
            if (reservation.getEmergencyPatient().getBloodDonation() == 0){
                throw new ApiException("sorry you cannot reservation this emergency case");
            }
            reservation.setStatus(status);
            reservation.getEmergencyPatient().setBloodDonation(reservation.getEmergencyPatient().getBloodDonation() - 1);
            resRepository.save(reservation);

            return "status change to " + status;
        }

        return "invalid hospital id";

    }


    // hospital can rate a volunteer
    public void rateVolunteer(Integer hospital_id, Integer hospitalVolunteer_id ,Integer volunteer_id, Double rating) {
        HospitalVolunteer hospitalVolunteer = hospitalVolunteerRepository.findHospitalVolunteerById(hospitalVolunteer_id);
        VolunteeringRequest volunteeringRequest = volunteeringRequestRepository.findByVolunteeringIdAndHospitalVolunteerId(volunteer_id, hospitalVolunteer_id);

        if (hospitalVolunteer == null || volunteeringRequest == null)
            throw new ApiException("Volunteer or Volunteering request not found");

        if (!hospitalVolunteer.getHospital().getId().equals(hospital_id))
            throw new ApiException("Hospital is not authorized to rate this volunteer");

        if(rating < 0 || rating > 5)
            throw new ApiException("Rating should be between 0 and 5");

        // calculate new average rating for volunteer
        double averageRating = volunteeringRequest.getVolunteering().getAverageRating() == null ? 0 : volunteeringRequest.getVolunteering().getAverageRating();
        int ratingCount = volunteeringRequest.getVolunteering().getRatingCount() == null ? 0 : volunteeringRequest.getVolunteering().getRatingCount();

        double newAverageRating = (averageRating * ratingCount + rating) / (ratingCount + 1);
        volunteeringRequest.getVolunteering().setAverageRating(newAverageRating);
        volunteeringRequest.getVolunteering().setRatingCount(ratingCount + 1);

        volunteeringRepository.save(volunteeringRequest.getVolunteering());
    }


}
