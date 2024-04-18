package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.Volunteering;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteeringRepository extends JpaRepository<Volunteering , Integer> {

    Volunteering findVolunteeringById(Integer id);


}
