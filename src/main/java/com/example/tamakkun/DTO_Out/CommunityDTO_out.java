package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommunityDTO_out {

    private String name;
    private String description;
    private String rules;
    private LocalDateTime createdAt;


}
