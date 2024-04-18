package com.example.blooddonationsystem.Service;


import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.Hospital;
import com.example.blooddonationsystem.Model.HospitalVolunteer;
import com.example.blooddonationsystem.Repository.HospitalRepository;
import com.example.blooddonationsystem.Repository.HospitalVolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalVolunteerService {


    private final HospitalVolunteerRepository hospitalVolunteerRepository ;
    private final HospitalRepository hospitalRepository ;



    public List<HospitalVolunteer> getAllHospitalVolunteer() {
        return hospitalVolunteerRepository.findAll();
    }

    public void addHospitalVolunteer(HospitalVolunteer volunteering, Integer hospital_id) {
        Hospital hospital = hospitalRepository.findHospitalById(hospital_id);

        if (hospital == null){
            throw new ApiException("invalid hospital id");
        }

        volunteering.setHospital(hospital);

        hospitalVolunteerRepository.save(volunteering);
    }

    public void updateHospitalVolunteer(Integer id, HospitalVolunteer volunteering) {
        HospitalVolunteer volunteering1 = hospitalVolunteerRepository.findHospitalVolunteerById(id);

        if (volunteering1 == null) {
            throw new ApiException("Hospital Volunteer not found");
        }

        volunteering1.setTitle(volunteering.getTitle());
        volunteering1.setVolunteerTasks(volunteering.getVolunteerTasks());
        volunteering1.setNumberOfVolunteers(volunteering.getNumberOfVolunteers());
        volunteering1.setStartDate(volunteering.getStartDate());


        hospitalVolunteerRepository.save(volunteering1);

    }

    public void deleteHospitalVolunteer(Integer id) {
        HospitalVolunteer volunteering1 = hospitalVolunteerRepository.findHospitalVolunteerById(id);

        if (volunteering1 == null) {
            throw new ApiException("Hospital Volunteer not found");
        }

        hospitalVolunteerRepository.delete(volunteering1);
    }


}
