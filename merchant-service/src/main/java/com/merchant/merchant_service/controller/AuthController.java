package com.merchant.merchant_service.controller;

import com.merchant.merchant_service.security.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenGenerator jwtTokenGenerator;

    // Inject the JwtTokenGenerator
    @Autowired
    public AuthController(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId) {
        // Generate JWT token after successful authentication
        String token = jwtTokenGenerator.generateToken(userId);
        return token; // Return the token as a response
    }
}
