package com.example.tamakkun.DTO_In;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor

public class CentreDTO_In {



    @NotEmpty(message = "Username is required!")
    @Size(min = 4, max = 10, message = "username must be between 4 and 10")
    private String username;

    @NotEmpty(message = "Password is required!")
    @Size(min = 6, max = 20, message = "password must be between 6 and 20")
    private String password;


    @NotEmpty(message = "Email is required!")
    @Email(message = "Must be a valid email format!")
    private String email;



    @NotEmpty(message = "Centre name is required!")
    @Size(max = 50, message = "Centre name cannot exceed 50 characters!")
    private String name;

    @NotEmpty(message = "Centre description is required!")
    @Size(max = 500, message = "Description cannot exceed 500 characters!")
    private String description;

    @NotEmpty(message = "Centre address is required!")
    @Size(max = 100, message = "Address cannot exceed 100 characters!")
    private String address;


    @JsonFormat(pattern = "HH:mm")
    private LocalTime openingHour;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closingHour;

    @NotEmpty(message = "commercial license number is required!")
    private String commercialLicense;


    @NotEmpty(message = "Phone Number is required!")
    @Pattern(regexp = "^(\\+966|0)?5\\d{8}$",  message = "Phone number must start with +966 or 05 and be followed by 8 digits")
    private String phoneNumber;

    private Double pricePerHour;

    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be a valid URL!")
    private String imageUrl;








}
