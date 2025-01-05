package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Specialist {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Specialist name is required!")
    @Size(max = 20, message = "Specialist name cannot exceed 20 characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty(message = "Specialization is required!")
    @Size(max = 50, message = "Specialization name cannot exceed 50 characters!")
    @Column(columnDefinition = "varchar(50) not null")
    private String specialization;

    @NotEmpty(message = "PhoneNumber is required!")
    @Pattern(regexp = "^(\\+966|0)?5\\d{8}$",   message = "Phone number must start with +966 or 05 and be followed by 8 digits")
    @Column(columnDefinition = "varchar(13) not null unique")
    private String phoneNumber;

    @Min(value = 4, message = "Experience years must be at least 4 years")
    @Column(columnDefinition = "int not null")
    private Integer experienceYears;


    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL!")
    @Column(columnDefinition = "varchar(100) not null")
    private String imageUrl;

    /////

    @ElementCollection
    @CollectionTable(name = "supported_disabilities", joinColumns = @JoinColumn(name = "specialist_id"))
    @Column(name = "supportedDisabilities")
    private List<@Pattern(regexp = "(?i)SPEECH_AND_LANGUAGE_DISORDERS|PHYSICAL_DISABILITY|INTELLECTUAL_DISABILITY|SENSORY_DISABILITY|AUTISM|DOWN_SYNDROME", message = "Invalid disability type") String> supportedDisabilities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist")
    private Set<BookingDate> bookingDates;

    @ManyToOne
    @JsonIgnore
    private Centre centre;







}
