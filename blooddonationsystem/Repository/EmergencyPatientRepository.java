package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.EmergencyPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyPatientRepository extends JpaRepository<EmergencyPatient , Integer> {

    EmergencyPatient findEmergencyPatientById(Integer id);

    List<EmergencyPatient> findEmergencyPatientsByHospitalIdAndStatus(Integer hospital_id, String status);

    List<EmergencyPatient> getEmergencyPatientsByEmergencyStatus(String status);

    List<EmergencyPatient> findAllByOrderByDateAsc();

    List<EmergencyPatient> findAllByBloodType(String bloodType);

    List<EmergencyPatient> findEmergencyPatientsByHospital_City(String city);

}
