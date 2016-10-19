package com.nirdosh.gateway.api;

import com.nirdosh.gateway.domain.model.JwtFilter;
import com.nirdosh.gateway.domain.model.LoginResponse;
import com.nirdosh.gateway.domain.model.User;
import com.nirdosh.gateway.domain.model.UserLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${service.authentication-service}:${service.authentication-service.port}")
    private String authenticationService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST)
    public LoginResponse login(@RequestBody final UserLogin login)
            throws ServletException {
        User user = restTemplate.getForObject(getEndpoint(login), User.class);
        if(user != null) {
            return getLoginResponse(login, user);
        }else{
            throw new ServletException("Login falied");
        }
    }

    private String getEndpoint(@RequestBody UserLogin login) {
        StringBuilder builder = new StringBuilder("http://")
                .append(authenticationService)
                .append("?")
                .append("username").append("=").append(login.name)
                .append("&")
                .append("password").append("=").append(login.password);
        return builder.toString();
    }

    private LoginResponse getLoginResponse(@RequestBody UserLogin login, User user) {
        return new LoginResponse(Jwts.builder().setSubject(login.name)
                .claim("roles", user.id).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JwtFilter.SECRET_KEY).compact());
    }
}
