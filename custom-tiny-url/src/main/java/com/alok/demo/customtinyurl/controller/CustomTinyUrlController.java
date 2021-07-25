package com.alok.demo.customtinyurl.controller;

import com.alok.demo.customtinyurl.entity.*;
import com.alok.demo.customtinyurl.exception.*;
import com.alok.demo.customtinyurl.service.*;
import lombok.extern.slf4j.*;
import org.apache.commons.validator.routines.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.*;

@RestController()
@RequestMapping(value = "/api/v1")
@Slf4j
@CrossOrigin
public class CustomTinyUrlController {

    UrlHashService urlHashService;

    public CustomTinyUrlController(UrlHashService urlHashService) {
        this.urlHashService = urlHashService;
    }

    @GetMapping(value = "/{hashedId}")
    public void get(@PathVariable String hashedId, HttpServletResponse httpServletResponse) throws CustomTinyUrlException {
        CustomTinyUrlResponse customTinyUrlResponse = urlHashService.getActualUrl(hashedId);
        if(customTinyUrlResponse == null){
            log.error("Invalid url id passed");
            throw new CustomTinyUrlException(CustomTinyUrlErrorCode.INVALID_HASHED_URL);
        }
        httpServletResponse.setHeader("Location", customTinyUrlResponse.getActualUrl());
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setStatus(302);
    }
    @PostMapping( value = "/create")
    public ResponseEntity<CustomTinyUrlResponse> generateShortUrl(@RequestBody String url) throws CustomTinyUrlException {
        log.info("Received request for url generation {}", url);
        UrlValidator urlValidator = new UrlValidator();
        boolean isValidUrl = urlValidator.isValid(url);
        if(!isValidUrl){
            log.error("Invalid url passed");
            throw new CustomTinyUrlException(CustomTinyUrlErrorCode.INVALID_URL);
        }
        CustomTinyUrlResponse customTinyUrlResponse = urlHashService.generateHashOfUrl(url);
        log.info("Successfully generated custom tiny url");
        return new ResponseEntity<>(customTinyUrlResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<CustomTinyUrlResponse>> getAllUrlGenerated(){
        log.info("Received request to fetch all url");
        List<CustomTinyUrlResponse> responseList = urlHashService.getAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
