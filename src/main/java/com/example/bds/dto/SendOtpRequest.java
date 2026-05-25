package com.example.bds.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendOtpRequest {

    @NotBlank(message = "username khong duoc de trong ")
    private String username;

    @NotBlank(message = "password khong duoc de trong")
    private String password;

    @Email
    private String email;
}
