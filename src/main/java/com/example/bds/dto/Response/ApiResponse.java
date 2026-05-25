package com.example.bds.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;


    public static <T> ApiResponse<T> success(T data, String message) {
return  new ApiResponse<>(200,message,data);


    }
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code,message,null);

    }



}
