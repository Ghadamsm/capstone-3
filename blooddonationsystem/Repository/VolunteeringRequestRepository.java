package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.VolunteeringRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteeringRequestRepository extends JpaRepository<VolunteeringRequest, Integer> {

    VolunteeringRequest findVolunteeringRequestById(Integer id);

    VolunteeringRequest findByVolunteeringIdAndHospitalVolunteerId(Integer volunteeringId, Integer hospitalVolunteerId);

}
