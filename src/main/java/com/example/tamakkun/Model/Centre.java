package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Locale;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Centre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Centre name is required!")
    @Size(max = 50, message = "Centre name cannot exceed 50 characters!")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;


    @NotEmpty(message = "Centre description is required!")
    @Size(max = 500, message = "Description cannot exceed 500 characters!")
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotEmpty(message = "Centre address is required!")
    @Size(max = 100, message = "Address cannot exceed 100 characters!")
    @Column(columnDefinition = "varchar(100) not null")
    private String address;

    @Column(columnDefinition = "varchar(20) not null")
    private LocalTime openingHour;

    @Column(columnDefinition = "varchar(20) not null")
    private LocalTime closingHour;

    @Column(columnDefinition = "varchar(10) not null")
    private String commercialLicense;

    @Column(columnDefinition = "varchar(13) not null")
    private String phoneNumber;

    @Column(columnDefinition = "Boolean")
    private Boolean isVerified=false;

    @Column(columnDefinition = "DOUBLE not null")
    private Double pricePerHour;

    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL!")
    @Column(columnDefinition = "varchar(100) not null")
    private String imageUrl;

    ///////////////

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<Activity> activitySet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<Specialist>specialists;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<BookingDate> bookingDates;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centre")
    private Set<Review> reviews;





}