package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BookingDTO_Out {

    private String parentName;
    private String childName;
    private String specialistName;
    private LocalDate bookingDate;
    private LocalTime bookingTime;
    private Integer hours;
    private Double totalPrice;
}
