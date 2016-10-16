package com.nirdosh.gateway.api;

import com.nirdosh.gateway.domain.service.ServiceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {

    @Autowired
    private ServiceResolver serviceResolver;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/api/devotee")
    public String getDevotees() {
        return restTemplate.getForObject(serviceResolver.serviceUrl("devotee-service"), String.class);
    }
}

