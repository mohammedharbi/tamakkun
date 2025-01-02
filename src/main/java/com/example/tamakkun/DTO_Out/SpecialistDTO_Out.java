package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SpecialistDTO_Out {


    private String name;

    private String Specialization;

    private Integer experienceYears;

    private String imageUrl;

    private Set<String> supportedDisabilities;




}
