package com.example.bds.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "email khong duoc de trong ")
    @Email(message = " email khong hop le ")
    private String email1;

    @NotBlank(message = "otp khong duoc bo trong")
    @Pattern(regexp = "\\d{6}", message = "otp phai la nhung con so")
    private String otp;

    @NotBlank
    private String username;

    @NotBlank
    @Pattern(
     regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
     message = "mật khẩu phải có các chữ số, tối thiểu 8 kí tự"
    )
    private String password;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "mật khẩu phải có các chữ số, tối thiểu 8 kí tự"
    )
    private String confirmPassword;

}
