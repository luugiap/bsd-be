package com.example.bds.config.exception;


import com.example.bds.dto.Response.ApiResponse;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<String>> ApplicationException(AppException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getErrorCode().getStatus().value(), ex.getErrorCode().getMessage())
                , ex.getErrorCode().getStatus());

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidateException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fileName = ((FieldError) error).getField();
            String message =  error.getDefaultMessage();
            errors.put(fileName, message);
        });

        ApiResponse<Map<String,String>> apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Lỗi Validate");
        apiResponse.setData(errors);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }



}
