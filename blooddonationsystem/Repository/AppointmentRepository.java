package com.example.blooddonationsystem.Repository;

import com.example.blooddonationsystem.Model.Appointment;
import com.example.blooddonationsystem.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    Appointment findAppointmentById(Integer id);

    List<Appointment> findAppointmentsByBloodBankId(Integer bloodBankId);

    List<Appointment> findAppointmentsByUserId(Integer user_id);

    List<Appointment> findAppointmentsByAttendedEqualsIgnoreCase(String isAttended);


}
