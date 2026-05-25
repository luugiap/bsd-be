package com.example.bds.controller;

import com.example.bds.config.exception.AppException;
import com.example.bds.config.exception.ErrorCode;
import com.example.bds.dto.Request.ForgotPasswordRequest;
import com.example.bds.dto.Request.ForgotPasswordStepRequest;
import com.example.bds.dto.Request.LoginRequest;
import com.example.bds.dto.Request.RegisterRequest;
import com.example.bds.dto.Response.ApiResponse;
import com.example.bds.dto.Response.LoginResponse;
import com.example.bds.dto.Response.UrlResponse;
import com.example.bds.dto.Response.UserResponse;
import com.example.bds.entity.rbac.Users;
import com.example.bds.repository.UserRepository;
import com.example.bds.service.OtpServices;
import com.example.bds.service.RateLimiterService;
import com.example.bds.service.UserServices;
import com.example.bds.utils.UploadFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {





    private final RateLimiterService rateLimiterService;
    private final UserRepository userRepository;
    private final OtpServices otpServices;
    private final UserServices userServices;
    private final UploadFile uploadFile;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
       String key = httpServletRequest.getRemoteAddr();
       boolean allowed = rateLimiterService.allowedRequest(key);

       if(!allowed){
           throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"TOO_MANY_REQUESTS");

       }
       UserResponse userResponse = userServices.registerWithEmail(registerRequest);
       return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/step1")
    public CompletableFuture<ResponseEntity<String>> registStep1(@RequestBody  RegisterRequest registerRequest,  HttpServletRequest httpServletRequest)  {

        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent() && userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "thông tin người dùng đã bị trùng");
        }
        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new AppException(ErrorCode.VALIDATION_ERROR, "mật khẩu không khớp");
        }



        String key = httpServletRequest.getRemoteAddr();
        boolean allowed = rateLimiterService.allowedRequest(key);
        if(!allowed){
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"TOO_MANY_REQUESTS");

        }
        return userServices.register(registerRequest.getUsername(), registerRequest.getPassword(),  registerRequest.getEmail());

    }
    @PostMapping("forgotPasswordStep")
    public ResponseEntity<String> forgotPasswordStep(@RequestBody ForgotPasswordStepRequest forgotPasswordStepRequest)  throws Exception {
        return userServices.forgotPasswordStep(forgotPasswordStepRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest,
                                                           HttpServletRequest httpServletRequest) throws Exception {

        String key = httpServletRequest.getRemoteAddr();
        boolean allowed = rateLimiterService.allowedRequest(key);

        if (!allowed) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Too many login requests"
            );
        }




        LoginResponse loginResponse = userServices.login(loginRequest);
        ApiResponse<LoginResponse> apiResponse = ApiResponse.success(loginResponse,"Đăng nhập thành công");

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/upload")
    public ResponseEntity<UrlResponse> upload (){

        UrlResponse urlResponse = uploadFile.generateUrl();
        return ResponseEntity.ok(urlResponse);
    }

    @PostMapping("/forgot")
    public UserResponse forgot(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        return userServices.forgotPassword(forgotPasswordRequest);
    }



}
