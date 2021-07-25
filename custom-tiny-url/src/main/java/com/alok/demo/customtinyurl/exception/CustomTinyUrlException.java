package com.alok.demo.customtinyurl.exception;

import lombok.*;
import org.springframework.http.*;

@Getter
public class CustomTinyUrlException extends Exception{
    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public CustomTinyUrlException() {
    }

    public CustomTinyUrlException(CustomTinyUrlErrorCode customTinyUrlErrorCode) {
        this.errorCode = customTinyUrlErrorCode.getErrorCode();
        this.errorMessage = customTinyUrlErrorCode.getErrorMessage();
        this.httpStatus = customTinyUrlErrorCode.getHttpStatus();
    }
}
