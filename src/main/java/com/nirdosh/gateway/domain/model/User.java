package com.nirdosh.gateway.domain.model;

import java.util.List;

public class User {
    public String id;
    public String userName;
    public String password;
    public List<String> roles;

    public User(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }
}