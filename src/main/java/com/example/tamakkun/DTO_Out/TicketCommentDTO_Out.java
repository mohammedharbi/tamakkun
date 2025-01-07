package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TicketCommentDTO_Out {
    private String content;
    private LocalDate createdAt;
    private String createdBy;



}
