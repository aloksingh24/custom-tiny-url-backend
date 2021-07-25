package com.alok.demo.customtinyurl.exception;

import lombok.*;

@Data
@NoArgsConstructor
@Setter
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
