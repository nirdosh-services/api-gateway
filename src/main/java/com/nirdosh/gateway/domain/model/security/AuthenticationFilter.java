package com.nirdosh.gateway.domain.model.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
@Component
public class AuthenticationFilter extends GenericFilterBean {

    public static final String SECRET_KEY = "this_is_my_secret_key_to_test_123";
    public static final String BEARER = "Bearer ";

    @Autowired
    Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.authentication-service}")
    private String authenticationService;

    @Value("${service.authentication-service.port}")
    private String port;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {


        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }


        final String token = authHeader.substring(BEARER.length());

        try {
            final String claims = restTemplate.getForObject(getEndpoint(token), String.class);
            request.setAttribute("claims", claims);
        } catch (SignatureException se) {
            throw new ServletException("invalid token");
        }

        filterChain.doFilter(request, servletResponse);
    }

    public String getEndpoint(String token) {
        return new StringBuilder("http://")
                .append(authenticationService)
                .append(":")
                .append(port)
                .append("/")
                .append("validate")
                .append("?")
                .append("token")
                .append("=")
                .append(token)
                .toString();
    }
}
