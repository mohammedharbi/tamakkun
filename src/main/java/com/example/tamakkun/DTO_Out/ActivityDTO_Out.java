package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class ActivityDTO_Out {


    private String name;

    private String description;

    private List< String> allowedDisabilities;




}
