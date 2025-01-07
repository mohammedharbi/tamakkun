package com.example.tamakkun.DTO_In;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDTO_In {

    @Future
    private LocalDateTime startTime;

    private Integer hours;

    private Boolean notifyMe;

}
