package com.nirdosh.gateway.api;

import com.nirdosh.gateway.domain.model.UserLogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${service.authentication-service}:${service.authentication-service.port}")
    private String authenticationService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public String login(@RequestBody final UserLogin login)
            throws ServletException {
        return restTemplate.getForObject(getEndpoint(login), String.class);
    }

    private String getEndpoint(@RequestBody UserLogin login) {
        StringBuilder builder = new StringBuilder("http://")
                .append(authenticationService).append("/")
                .append("authentication")
                .append("?")
                .append("username").append("=").append(login.name)
                .append("&")
                .append("password").append("=").append(login.password);
        return builder.toString();
    }
}
