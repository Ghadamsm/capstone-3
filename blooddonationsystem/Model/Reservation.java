package com.example.blooddonationsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Pattern(regexp = "^(pending|accepted|rejected|canceled)$")
    @Column(columnDefinition = "varchar(9) check(status in ('pending','accepted','rejected','canceled'))")
    private String status;



    //    relations

    @ManyToOne
    @JsonIgnore
    private EmergencyPatient emergencyPatient;

    @ManyToMany
    private Set<User> users = new HashSet<>();


}
