package com.example.tamakkun.DTO_In;

import com.example.tamakkun.Model.MyUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TicketDTO_In {

    @NotEmpty(message = "description is required!")
    private String description;




}
