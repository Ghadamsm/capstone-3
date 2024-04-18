package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.HospitalVolunteer;
import com.example.blooddonationsystem.Model.Volunteering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalVolunteerRepository extends JpaRepository<HospitalVolunteer , Integer> {

    HospitalVolunteer findHospitalVolunteerById(Integer id);

    HospitalVolunteer getAllHospitalVolunteerById(Integer hospitalVolunteer_id);

}
