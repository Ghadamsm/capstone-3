package com.example.blooddonationsystem.Service;


import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.Hospital;
import com.example.blooddonationsystem.Model.HospitalVolunteer;
import com.example.blooddonationsystem.Model.Volunteering;
import com.example.blooddonationsystem.Model.VolunteeringRequest;
import com.example.blooddonationsystem.Repository.HospitalRepository;
import com.example.blooddonationsystem.Repository.HospitalVolunteerRepository;
import com.example.blooddonationsystem.Repository.VolunteeringRepository;
import com.example.blooddonationsystem.Repository.VolunteeringRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class
VolunteeringService {


    private final VolunteeringRepository volunteeringRepository ;
    private final HospitalVolunteerRepository hospitalVolunteerRepository;
    private final HospitalRepository hospitalRepository;
    private final VolunteeringRequestRepository volunteeringRequestRepository;



    public List<Volunteering> getAllVolunteering() {
        return volunteeringRepository.findAll();
    }

    public void addVolunteering(Volunteering volunteering) {
        volunteeringRepository.save(volunteering);
    }

    public void updateVolunteering(Integer id, Volunteering volunteering) {
        Volunteering volunteering1 = volunteeringRepository.findVolunteeringById(id);

        if (volunteering1 == null) {
            throw new ApiException("Volunteering not found");
        }

        volunteering1.setName(volunteering.getName());
        volunteering1.setAge(volunteering.getAge());
        volunteering1.setEmail(volunteering.getEmail());
        volunteering1.setMajor(volunteering.getMajor());
        volunteering1.setPhoneNumber(volunteering.getPhoneNumber());


        volunteeringRepository.save(volunteering1);
    }


    public void deleteVolunteering(Integer id) {
        Volunteering volunteering1 = volunteeringRepository.findVolunteeringById(id);

        if (volunteering1 == null) {
            throw new ApiException("Volunteering not found");
        }
        volunteeringRepository.delete(volunteering1);
    }





    //هياء endpoint
    public void applyForVolunteering(Integer volunteering_id, Integer hospitalVolunteer_id) {
        Volunteering volunteering = volunteeringRepository.findVolunteeringById(volunteering_id);
        HospitalVolunteer hospitalVolunteer = hospitalVolunteerRepository.findHospitalVolunteerById(hospitalVolunteer_id);
        VolunteeringRequest volunteeringRequest = volunteeringRequestRepository.findByVolunteeringIdAndHospitalVolunteerId(volunteering_id, hospitalVolunteer_id);

        if(volunteeringRequest != null)
            throw new ApiException("Volunteering request already exists");

        if (volunteering == null || hospitalVolunteer == null)
            throw new ApiException("Volunteering or HospitalVolunteer not found");

        VolunteeringRequest volunteeringRequest2 = new VolunteeringRequest();
        volunteeringRequest2.setVolunteering(volunteering);
        volunteeringRequest2.setHospitalVolunteer(hospitalVolunteer);
        volunteeringRequest2.setStatus("Pending");

        volunteeringRequestRepository.save(volunteeringRequest2);
    }

    public String getVolunteeringStatus(Integer volunteering_id, Integer hospitalVolunteer_id) {
        VolunteeringRequest volunteeringRequest = volunteeringRequestRepository.findByVolunteeringIdAndHospitalVolunteerId(volunteering_id, hospitalVolunteer_id);

        if (volunteeringRequest == null) {
            throw new ApiException("Volunteering request not found");
        }

        return volunteeringRequest.getStatus();
    }

    public void rateHospital(Integer volunteering_id, Integer hospital_id, Double rating) {
        Hospital hospital=hospitalRepository.findHospitalById(hospital_id);


        Volunteering volunteering = volunteeringRepository.findVolunteeringById(volunteering_id);
        if (volunteering == null) {
            throw new ApiException("Volunteering not found");
        }

        if (hospital == null) {
            throw new ApiException("Hospital not found");
        }

        if (rating < 0 || rating > 5) {
            throw new ApiException("Rating must be between 0 and 5");
        }


        double averageRating = hospital.getAverageRating() == null ? 0 : hospital.getAverageRating();
        int ratingCount = hospital.getRatingCount() == null ? 0 : hospital.getRatingCount();

        double newAverageRating = ((averageRating * ratingCount) + rating) / (ratingCount + 1);
        hospital.setAverageRating(newAverageRating);
        hospital.setRatingCount(ratingCount + 1);

        hospital.setAverageRating(newAverageRating);


        hospitalRepository.save(hospital);
    }


    public Hospital getHighestRatedHospital() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        Hospital highestRatedHospital = null;
        double highestRating = 0;

        for (Hospital hospital : hospitals) {
            if (hospital.getAverageRating() != null && hospital.getAverageRating() > highestRating) {
                highestRating = hospital.getAverageRating();
                highestRatedHospital = hospital;
            }
        }

        return highestRatedHospital;
    }



}
