package com.example.blooddonationsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @FutureOrPresent(message = "Date should be in the future")
    @Column(columnDefinition = "date")
    private LocalDateTime dateTime;


    @Pattern(regexp = "^(unattended|attended)$")
    @Column(columnDefinition = "varchar(20) check(attended in ('unattended','attended'))")
    private String attended;

    @Pattern(regexp = "^(scheduled|rejected|cancelled)$")
    @Column(columnDefinition = "varchar(20) check(status in ('scheduled','rejected','cancelled'))")
    private String status;


    @Column(columnDefinition = "double")
    private Double rating;



    //    relations

    @ManyToOne
    @JsonIgnore
    private BloodBank bloodBank;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Volunteering volunteering;
}
