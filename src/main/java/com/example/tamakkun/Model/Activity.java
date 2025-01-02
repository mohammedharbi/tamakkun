package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Activity name is required!")
    @Size(max = 30, message = "Activity name cannot exceed 30 characters!")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;

    @NotEmpty(message = "Activity description is required!")
    @Size(max = 500, message = "Activity cannot exceed 500 characters!")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotEmpty(message = "Disability type is required!")
    @Size(max = 30, message = "Activity cannot exceed 30 characters!")
    @Column(columnDefinition = "varchar(30) not null")
    private String disabilityType;



    @ManyToOne
    @JsonIgnore
    private Centre centre;










}
