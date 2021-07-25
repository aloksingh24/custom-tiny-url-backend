package com.alok.demo.customtinyurl.service;

import com.alok.demo.customtinyurl.entity.*;
import com.google.common.hash.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;

import java.nio.charset.*;
import java.util.*;
import java.util.stream.*;

@Service
@Slf4j
public class UrlHashService {
    RedisTemplate<String, CustomTinyUrlResponse> redisTemplate;

    @Value("${server.port}")
    private Integer portNumber;
    public UrlHashService(RedisTemplate<String, CustomTinyUrlResponse> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CustomTinyUrlResponse generateHashOfUrl(String url){
        String generatedHash =  Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        log.debug("Generated hashed url {} for {}", generatedHash, url);
        StringBuilder shortUrl = new StringBuilder();
        shortUrl.append("http://localhost:");
        shortUrl.append(portNumber);
        shortUrl.append("/api/v1/");
        shortUrl.append(generatedHash);
        CustomTinyUrlResponse customTinyUrlResponse =  new CustomTinyUrlResponse(generatedHash, shortUrl.toString(), url, 0);
        redisTemplate.opsForValue().set(customTinyUrlResponse.getHash(), customTinyUrlResponse);
        return  customTinyUrlResponse;
    }

    public CustomTinyUrlResponse getActualUrl(String hashedUrl){
        CustomTinyUrlResponse  tinyUrlResponse =  redisTemplate.opsForValue().get(hashedUrl);
        if(tinyUrlResponse != null){
            tinyUrlResponse.setAccessCount(tinyUrlResponse.getAccessCount()+1);
            redisTemplate.opsForValue().set(tinyUrlResponse.getHash(), tinyUrlResponse);
        }
        return tinyUrlResponse;
    }

    public List<CustomTinyUrlResponse> getAll(){
        Set<String> set = redisTemplate.keys("*");
        log.debug("Total key fetched size {}", set.size());
        List<CustomTinyUrlResponse> tinyUrlResponses = set.stream().map(key-> redisTemplate.opsForValue().get(key)).collect(Collectors.toList());
        return tinyUrlResponses;
    }
}
