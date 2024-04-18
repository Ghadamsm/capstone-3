package com.example.blooddonationsystem.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class HospitalVolunteer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;


    @NotEmpty(message = "title should be not empty")
    @Size(min = 10 , message = "title should be more than 10 char")
    @Column(columnDefinition = "varchar(50) not null")
    private String title ;


    @NotEmpty(message = "volunteer Tasks should be not empty")
    @Size(min = 10 , message = "volunteer Tasks should be more than 10 char")
    @Column(columnDefinition = "varchar(250) not null")
    private String volunteerTasks ;


    @NotNull(message = "number Of Volunteers should be not empty")
    @Positive(message = "positive number only")
    @Column(columnDefinition = "int not null")
    private Integer numberOfVolunteers ;

    @FutureOrPresent
    @Column(columnDefinition = "date")
    private LocalDate startDate ;



//    relations

    @ManyToOne
    @JsonIgnore
    private Hospital hospital;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospitalVolunteer")
    private Set<VolunteeringRequest> volunteeringRequests = new HashSet<>();

}
