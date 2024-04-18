package com.example.blooddonationsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VolunteeringRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(20)")
    private String status; // pending, accepted, rejected



    //    relations

    @ManyToOne
    @JsonIgnore
    private Volunteering volunteering;

    @ManyToOne
    @JsonIgnore
    private HospitalVolunteer hospitalVolunteer;


}