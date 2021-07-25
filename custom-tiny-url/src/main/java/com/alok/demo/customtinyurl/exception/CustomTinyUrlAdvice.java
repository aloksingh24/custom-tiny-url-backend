package com.alok.demo.customtinyurl.exception;

import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@Slf4j
public class CustomTinyUrlAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionResponse(CustomTinyUrlException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(exception.getErrorCode());
        errorResponse.setErrorMessage(exception.getErrorMessage());
        return new ResponseEntity<>(errorResponse, exception.getHttpStatus());
    }
}
