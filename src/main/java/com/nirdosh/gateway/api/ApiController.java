package com.nirdosh.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/devotee")
    public String getDevotees() {
        String devoteeEndPoint = "http://devotee-service:8080/devotee";
        return restTemplate.getForObject(devoteeEndPoint, String.class);
    }
}

