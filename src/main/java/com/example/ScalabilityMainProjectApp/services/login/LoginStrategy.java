package com.example.UserService.services.login;

public interface LoginStrategy {
    String login(String identifier, String secret); // returns JWT token
}

