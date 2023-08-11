package com.example.groupproject.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final String API_URL = "https://uncovered-treasure-v1.p.rapidapi.com/topics";
    private final String API_KEY = "cbd72f4473mshe051356c670e17cp18724djsna234e4990082";  // replace with your actual API key

    public String fetchData() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", API_KEY);
        headers.set("X-RapidAPI-Host", "uncovered-treasure-v1.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public ApiService() {
    }

    public String getAPI_URL() {
        return API_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}
