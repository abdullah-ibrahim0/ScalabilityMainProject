package com.merchant.merchant_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenGenerator {

    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * Generates a JWT token for the given user ID
     *
     * @param userId The ID of the user (usually user ID or email)
     * @return The generated JWT token as a string
     */
    public String generateToken(String userId) {

        // Set expiration to 1 hour (can be adjusted)
        long expirationTime = 1000 * 60 * 60;  // 1 hour in milliseconds
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Generate and return the JWT token
        return Jwts.builder()
                .setSubject(userId)  // Set the user ID as the subject
                .setExpiration(expirationDate)  // Set expiration time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // Sign with HS256 algorithm and secret key
                .compact();  // Return the token as a string
    }

    public static void main(String[] args) {
        // You can test the generator here if necessary, but in a Spring app, it's usually used in a service class
        JwtTokenGenerator generator = new JwtTokenGenerator();
        String userId = "12345";  // Replace with actual user ID
        String token = generator.generateToken(userId);
        System.out.println("Generated Token: " + token);  // Print the generated token
    }
}
