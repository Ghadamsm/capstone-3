package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Integer> {

    BloodBank findBloodBankById(Integer id);

    List<BloodBank> findTop10ByOrderByAverageRatingDesc();
}
