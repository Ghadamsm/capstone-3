package com.example.blooddonationsystem.Controller;

import com.example.blooddonationsystem.Model.Appointment;
import com.example.blooddonationsystem.Service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {


    private final AppointmentService appointmentService;

    @GetMapping("/get")
    public ResponseEntity getAllAppointments() {
        return ResponseEntity.status(200).body(appointmentService.getAllAppointments());
    }

    @PostMapping("/book/{user_id}/{bloodbank_id}")
    public ResponseEntity bookAppointment(@RequestBody @Valid Appointment appointment, @PathVariable Integer user_id, @PathVariable Integer bloodbank_id) {
        appointmentService.bookAppointment(appointment,user_id,bloodbank_id);
        return ResponseEntity.status(200).body("Appointment booked successfully");
    }

    @PutMapping("/update/{appointment_id}/{user_id}")
    public ResponseEntity updateAppointment(@PathVariable Integer appointment_id,Integer user_id, @RequestBody Appointment appointment) {
        appointmentService.updateAppointment(appointment_id, appointment, user_id);
        return ResponseEntity.status(200).body("Appointment updated successfully");
    }

    @DeleteMapping("/delete/{appointment_id}/{user_id}")
    public ResponseEntity deleteAppointment(@PathVariable Integer appointment_id, @PathVariable Integer user_id) {
        appointmentService.deleteAppointment(appointment_id,user_id);
        return ResponseEntity.status(200).body("Appointment deleted successfully");
    }


//    endpoint


    // get user appointements
    @GetMapping("/get/{user_id}")
    public List<Appointment> getUserAppointments(@PathVariable Integer user_id) {
        return appointmentService.getUserAppointments(user_id);
    }

    // user can rate the appointment
    @PutMapping("/rate/{user_id}/{appointment_id}/{rating}")
    public ResponseEntity rateAppointment(@PathVariable Integer user_id,@PathVariable Integer appointment_id,@PathVariable Double rating) {
        appointmentService.rateAppointment(user_id,appointment_id,rating);
        return ResponseEntity.status(200).body("Appointment rated successfully");
    }

    // user can cancel the appointment
    @PutMapping("/cancel/{user_id}/{appointment_id}")
    public ResponseEntity cancelAppointment(@PathVariable Integer user_id,@PathVariable Integer appointment_id) {
        appointmentService.cancelAppointment(user_id,appointment_id);
        return ResponseEntity.status(200).body("Appointment cancelled successfully");
    }


    // get top-rated blood banks
    @GetMapping("/top-rated-blood-banks")
    public ResponseEntity getTopRatedBloodBanks() {
        return ResponseEntity.status(200).body(appointmentService.getTopRatedBloodBanks());
    }

}
