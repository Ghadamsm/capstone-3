package com.example.blooddonationsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot ne empty")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Email(message = "Email should be valid")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number should be valid")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @Min(value = 18, message = "Age should be greater than 18")
    @Column(columnDefinition = "int not null check(age > 18)")
    private Integer age;

    @NotEmpty(message = "City cannot be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String city;

    @NotEmpty(message = "Blood type cannot be empty")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood type should be valid")
    @Column(columnDefinition = "varchar(3) not null")
    private String bloodType;



    //    relations

    @ManyToMany
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Appointment> appointments = new HashSet<>();
}
