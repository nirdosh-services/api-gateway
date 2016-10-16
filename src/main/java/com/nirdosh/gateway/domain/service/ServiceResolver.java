package com.nirdosh.gateway.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ServiceResolver {

    @Autowired
    private DiscoveryClient discoveryClient;

    public URI serviceUrl(String serviceName){
          return discoveryClient.getInstances(serviceName).get(0).getUri();
    }
}
