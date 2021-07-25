package com.alok.demo.customtinyurl.exception;

import lombok.*;
import org.springframework.http.*;

@Getter
public enum CustomTinyUrlErrorCode {
    INVALID_URL("1001", "Incorrect url , please pass valid url content", HttpStatus.BAD_REQUEST),
    INVALID_HASHED_URL("1002", "Not url found for request, please provide valid input", HttpStatus.NOT_FOUND),
    ;

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    CustomTinyUrlErrorCode(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
