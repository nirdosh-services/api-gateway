package com.nirdosh.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.user-service}")
    private String userService;

    @Value("${service.user-service.port}")
    private String port;

    @RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUsers() {
        String devoteeEndPoint = getUserServiceEndPoint();
        return restTemplate.getForObject(devoteeEndPoint, String.class);
    }

    private String getUserServiceEndPoint() {
        return "http://"+userService+":"+port+"/user";
    }
}

