package com.nirdosh.gateway.domain.model;

public class LoginResponse {
    public String token;

    public LoginResponse(final String token) {
        this.token = token;
    }
}