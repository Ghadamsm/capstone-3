package com.example.blooddonationsystem.Controller;


import com.example.blooddonationsystem.Model.HospitalVolunteer;
import com.example.blooddonationsystem.Service.HospitalVolunteerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hospital-volunteer")
@RequiredArgsConstructor
public class HospitalVolunteerController {


    private final HospitalVolunteerService hospitalVolunteerService ;


    @GetMapping("/get-all")
    public ResponseEntity getAllHospitalVolunteer() {
        return ResponseEntity.status(200).body(hospitalVolunteerService.getAllHospitalVolunteer());
    }

    @PostMapping("/add/{hospitalId}")
    public ResponseEntity addHospitalVolunteer(@RequestBody @Valid HospitalVolunteer hospitalVolunteer, @PathVariable Integer hospitalId ){
        hospitalVolunteerService.addHospitalVolunteer(hospitalVolunteer, hospitalId);
        return ResponseEntity.status(200).body("Hospital Volunteer added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateHospitalVolunteer(@PathVariable Integer id, @RequestBody @Valid HospitalVolunteer hospitalVolunteer) {
        hospitalVolunteerService.updateHospitalVolunteer(id, hospitalVolunteer);
        return ResponseEntity.status(200).body("Hospital Volunteer updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteHospitalVolunteer(@PathVariable Integer id) {
        hospitalVolunteerService.deleteHospitalVolunteer(id);
        return ResponseEntity.status(200).body("Hospital Volunteer deleted successfully");
    }
}
