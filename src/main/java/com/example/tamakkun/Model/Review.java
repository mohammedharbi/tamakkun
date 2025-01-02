package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Positive(message = "rating must be a positive number")
    @Column(columnDefinition = "int not null")
    private Integer ratingCentre;

    @Positive(message = "rating must be a positive number")
    @Column(columnDefinition = "int not null")
    private Integer ratingSpecialist;

    @Size(max = 500, message = "Comment cannot exceed 500 characters!")
    @Column(columnDefinition = "varchar(500) not null")
    private String comment;

    @ManyToOne
    @JsonIgnore
    private Centre centre;

    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @ManyToOne
    @JsonIgnore
    private Specialist specialist;
}
