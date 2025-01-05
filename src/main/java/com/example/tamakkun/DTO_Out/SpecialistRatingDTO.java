package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialistRatingDTO {

    private Integer specialistId;
    private Double averageRating;
}
