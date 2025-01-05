package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Full name is required!")
    @Column(columnDefinition = "varchar(30) not null")
    private String fullName;

    @NotEmpty(message = "Disability type is required!")
    @Column(columnDefinition = "varchar(30) not null")
    @Pattern(regexp = "(?i)SPEECH_AND_LANGUAGE_DISORDERS|PHYSICAL_DISABILITY|INTELLECTUAL_DISABILITY|SENSORY_DISABILITY|AUTISM|DOWN_SYNDROME", message = "Invalid disability type")
    private String disabilityType;

    @NotNull(message = "Age is required!")
    @Max(15)
    @Column(columnDefinition = "int not null")
    private Integer age;



    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "child")
    private Set<Booking> bookings;

}
