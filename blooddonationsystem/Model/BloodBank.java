package com.example.blooddonationsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BloodBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot ne empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "City cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String city;


    @Column(columnDefinition = "boolean")
    private Boolean isThere ;

    @Column(columnDefinition = "date")
    private LocalDate date ;

    @Column(columnDefinition = "double")
    private Double averageRating;

    @Column(columnDefinition = "int")
    private Integer ratingCount;



//    relations
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloodBank")
    private Set<Appointment> appointments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bloodBank")
    private Set<AvailableSlot> availableSlots;
}
