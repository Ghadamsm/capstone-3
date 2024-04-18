package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.EmergencyPatient;
import com.example.blooddonationsystem.Model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Integer> {

    Hospital findHospitalById(int id);

}
