package com.example.bds.dto.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ForgotPasswordRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String otpCode;

    @NotBlank
    private String password;
}
