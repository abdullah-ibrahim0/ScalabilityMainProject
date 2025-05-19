package com.example.UserService.services.login;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class LoginContext {

    @Autowired
    private Map<String, LoginStrategy> strategies;

    public String login(String strategyKey, String id, String secret) {
        LoginStrategy strategy = strategies.get(strategyKey.toLowerCase());
        if (strategy == null) {
            throw new RuntimeException("Unsupported login method: " + strategyKey);
        }
        return strategy.login(id, secret);
    }
}
