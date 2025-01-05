package com.example.tamakkun.DTO_Out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class CentreDTO_Out {


    private String name;

    private String description;

    private String address;

    private LocalTime openingHour;

    private LocalTime closingHour;

    private Double pricePerHour;

    private String imageUrl;







}
