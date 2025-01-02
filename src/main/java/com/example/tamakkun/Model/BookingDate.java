package com.example.tamakkun.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class BookingDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Booking booking;

    @ManyToOne
    @JsonIgnore
    private Centre centre;

    @ManyToOne
    @JsonIgnore
    private Specialist specialist;
}
