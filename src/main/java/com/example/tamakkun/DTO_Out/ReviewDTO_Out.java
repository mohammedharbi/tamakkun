package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO_Out {

    private Integer ratingCentre;

    private Integer ratingSpecialist;

    private String comment;

    private String parentName;

    private String centreName;

    private String specialistName;
}
