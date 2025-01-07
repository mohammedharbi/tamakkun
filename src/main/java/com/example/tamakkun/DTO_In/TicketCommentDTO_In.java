package com.example.tamakkun.DTO_In;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketCommentDTO_In {

    @NotEmpty(message = "PostComment content is required!")
    private String content;
}
