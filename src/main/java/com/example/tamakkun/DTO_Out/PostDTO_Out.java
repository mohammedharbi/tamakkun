package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDTO_Out {
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String parentName;
}
