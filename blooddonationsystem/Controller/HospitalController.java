package com.example.blooddonationsystem.Controller;

import com.example.blooddonationsystem.Api.ApiResponse;
import com.example.blooddonationsystem.Model.Hospital;
import com.example.blooddonationsystem.Service.HospitalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/get-all")
    public ResponseEntity getAllHospitals() {
        return ResponseEntity.status(200).body(hospitalService.getAllHospitals());
    }

    @PostMapping("/add")
    public ResponseEntity addHospital(@RequestBody @Valid Hospital hospital) {
        hospitalService.addHospital(hospital);
        return ResponseEntity.status(200).body("Hospital added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateHospital(@PathVariable Integer id, @RequestBody @Valid Hospital hospital) {
        hospitalService.updateHospital(id, hospital);
        return ResponseEntity.status(200).body("Hospital updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteHospital(@PathVariable Integer id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.status(200).body("Hospital deleted successfully");
    }

    //    endpoint


    // get emergency patients cases that are completed for a hospital
    @GetMapping("/get-completed-cases/{hospital_id}")
    public ResponseEntity getCompletedEmergencyPatients(@PathVariable Integer hospital_id) {
        return ResponseEntity.status(200).body(hospitalService.getCompletedEmergencyPatients(hospital_id));
    }


    @PutMapping("/status-donation/{hospitalId}/{caseId}/{status}")
    public ResponseEntity changeBloodDonationStatus(@PathVariable Integer hospitalId ,@PathVariable Integer caseId ,@PathVariable String status){
        hospitalService.changeBloodDonationStatus(hospitalId, caseId, status);

        return ResponseEntity.status(200).body(new ApiResponse("status changed successfully"));
    }

    @PutMapping("/status-volunteer/{hospitalId}/{HvolunteerId}/{volunteerId}/{status}")
    public ResponseEntity AcceptingOrRejectingVolunteering(@PathVariable Integer hospitalId ,@PathVariable Integer HvolunteerId , @PathVariable Integer volunteerId ,@PathVariable String status ){

        return ResponseEntity.status(200).body(hospitalService.AcceptingOrRejectingVolunteering(hospitalId, HvolunteerId, volunteerId , status));
    }

    @PutMapping("/status-res/{hospitalId}/{reservationId}/{status}")
    public ResponseEntity AcceptingOrRejectingBloodDonation(@PathVariable Integer hospitalId ,@PathVariable Integer reservationId ,@PathVariable String status){
        return ResponseEntity.status(200).body(hospitalService.AcceptingOrRejectingBloodDonation(hospitalId, reservationId, status));
    }

    // hospital can rate a volunteer
    @PutMapping("/rate/{volunteer_id}/{hospital_id}/{hospitalVolunteer_id}/{rating}")
    public ResponseEntity rateVolunteer(@PathVariable Integer volunteer_id,@PathVariable Integer hospital_id, @PathVariable Integer hospitalVolunteer_id, @PathVariable Double rating) {
        hospitalService.rateVolunteer(hospital_id, hospitalVolunteer_id, volunteer_id,rating);
        return ResponseEntity.status(200).body("Volunteer rated successfully");
    }

}
