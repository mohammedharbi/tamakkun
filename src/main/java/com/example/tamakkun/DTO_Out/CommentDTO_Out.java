package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDTO_Out {
    private String content;
    private String parentName;
    private LocalDate createdAt;

}
