package com.example.tamakkun.DTO_In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParentDTO_In {

   // @Size(min = 4, max = 10, message = "Username must be between 4 and 10")
    private String username;

    //@NotEmpty(message = "Password is required!")
    //@Size(min = 6, max = 20, message = "Password must be between 6 and 20")
    private String password;

   // @Email(message = "Invalid Email")
    private String email;

//    @NotEmpty(message = "Name is required!")
    private String fullName;

//    @NotEmpty(message = "PhoneNumber is required!")
//    @Pattern(regexp = "^(\\+966|0)?5\\d{8}$",   message = "Phone number must start with +966 or 05 and be followed by 8 digits")
    private String phoneNumber;

//    @NotEmpty(message = "Address is required!")
    private String address;

}
