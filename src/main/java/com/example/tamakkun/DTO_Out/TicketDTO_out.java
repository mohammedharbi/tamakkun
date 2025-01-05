package com.example.tamakkun.DTO_Out;

import com.example.tamakkun.Model.MyUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TicketDTO_out {
    private String type;
    private String description;
    private String status;
    private LocalDate createdAt;
    private String createdBy;
    private String target;


}

