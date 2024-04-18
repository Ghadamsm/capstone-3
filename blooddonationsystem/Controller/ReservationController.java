package com.example.blooddonationsystem.Controller;

import com.example.blooddonationsystem.Model.Reservation;
import com.example.blooddonationsystem.Service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {



    private final ReservationService reservationService;

    @GetMapping("/get")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservation();
    }


    @PutMapping("/update/{id}")
    public void updateReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        reservationService.updateReservation(id, reservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }



    //    endpoint

    @PutMapping("/cancel/{userId}/{reservationId}")
    public ResponseEntity cancelReservation(@PathVariable Integer userId , @PathVariable Integer reservationId){
        return ResponseEntity.status(200).body(reservationService.cancelReservation(userId, reservationId));

    }

    @PostMapping("/add/{user_id}/{emergencyPatient_id}")
    public ResponseEntity addReservation(@PathVariable Integer user_id, @PathVariable Integer emergencyPatient_id) {
        reservationService.addReservation(user_id, emergencyPatient_id);
        return ResponseEntity.status(200).body("Reservation added successfully");
    }
}
