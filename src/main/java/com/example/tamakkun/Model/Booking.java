package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Future
    private LocalDateTime startTime;

    private Integer hours;

    private String status;

    private Double totalPrice;

    private Boolean notifyMe = false; //If parent want to send email about bokking reminder

    private Boolean isAlerted = false; // If email sent or not

    private Boolean isScanned = false; // Flag to track QR code usage


    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BookingDate bookingDate;

    @ManyToOne
    @JsonIgnore
    private Parent parent;

    @ManyToOne
    @JsonIgnore
    private Child child;

    @ManyToOne
    @JsonIgnore
    private Centre centre;




}