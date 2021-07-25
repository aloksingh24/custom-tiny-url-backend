package com.alok.demo.customtinyurl.entity;

import lombok.*;

@Getter
@AllArgsConstructor
@Setter
public class CustomTinyUrlResponse {
    private String hash;
    private String hashedUrl;
    private String actualUrl;
    private Integer accessCount;
}
