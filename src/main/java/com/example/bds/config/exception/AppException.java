package com.example.bds.config.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
private final ErrorCode errorCode;

private String message;

public AppException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.message = errorCode.getMessage();
}

public AppException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
}

}
