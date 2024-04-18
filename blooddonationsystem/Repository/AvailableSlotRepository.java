package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.AvailableSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AvailableSlotRepository extends JpaRepository<AvailableSlot, Integer> {
    List<AvailableSlot> findAvailableSlotsByBloodBankId(Integer bloodBankId);

    AvailableSlot findAvailableSlotById(Integer id);

    AvailableSlot findByBloodBankIdAndDateTime(Integer bloodBankId, LocalDateTime status);

}