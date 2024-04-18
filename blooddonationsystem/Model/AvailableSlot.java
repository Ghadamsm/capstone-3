package com.example.blooddonationsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AvailableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @FutureOrPresent(message = "Date should be in the future")
    @Column(columnDefinition = "datetime")
    private LocalDateTime dateTime;


    //    relations

    @ManyToOne
    @JsonIgnore
    private BloodBank bloodBank;
}