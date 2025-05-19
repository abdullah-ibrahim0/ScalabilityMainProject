package com.example.UserService.controllers;

import com.example.UserService.models.User;
import com.example.UserService.repositories.UserRepository;
import com.example.UserService.services.*;
import com.example.UserService.services.login.LoginContext;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private  UserService userService;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  EmailService emailService;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private LoginContext loginContext;

    @Autowired
    private MerchantProducer merchantProducer;


    /**
     * Register a new user and send verification email
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        System.out.println("Registering user: " + user);
        // print all the fields of the user object
        System.out.println("User details: ");

        userService.register(user);
        if ("MERCHANT".equalsIgnoreCase(user.getRole().toString())) {
            System.out.println("ALOOOOOO YASTAAAA");
            merchantProducer.sendMerchantCreatedEvent(user);
        }
        return ResponseEntity.ok("User registered. Please check your email to verify your account.");
    }

    /**
     * Verify user email using token
     */
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        try {
            Long userId = jwtService.extractUserId(token);
            User optionalUser = userRepository.findByUserId(userId);

            if (optionalUser == null) {
                return ResponseEntity.badRequest().body("Invalid verification link.");
            }

            User user = optionalUser;

            if (user.isEmailVerified()) {
                return ResponseEntity.ok("Email is already verified.");
            }

            if (jwtService.isTokenExpired(token)) {
                return ResponseEntity.badRequest().body("Token has expired.");
            }

            user.setEmailVerified(true);
            userRepository.save(user);

            return ResponseEntity.ok("Email verified successfully!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//        User optionalUser = userRepository.findByEmail(email);
//        if (optionalUser == null) {
//            return ResponseEntity.status(401).body("Invalid email or password.");
//        }
//
//        User user = optionalUser;
//
//        if (!user.isEmailVerified()) {
//            return ResponseEntity.status(403).body("Email not verified.");
//        }
//
//        if (user.getPassword() == null || !user.getPassword().equals(password)) {
//            return ResponseEntity.status(401).body("Invalid email or password.");
//        }
//
//        String token = jwtService.generateToken(user.getId(), user.getEmail());
//        sessionService.saveSession(token, user.getId());
//
//        return ResponseEntity.ok("Login successful. Your token: " + token);
//    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String method,     // "basic", "google", etc.
            @RequestParam String identifier, // email or token
            @RequestParam String secret      // password or unused
    ) {
        try {
            String token = loginContext.login(method, identifier, secret);
            return ResponseEntity.ok("Login successful. Token: " + token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        SessionService sessionService = SessionService.getInstance();
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body("Token is missing.");
        }
        Long userId = sessionService.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(401).body("Invalid token.");
        }
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }
        sessionService.deleteSession(token);
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    ) {
        SessionService sessionService = SessionService.getInstance();

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Long userId = sessionService.getUserIdFromToken(token);
        if (userId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }


        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        if (!user.getPassword().equals(oldPassword)) {
            return ResponseEntity.badRequest().body("Old password is incorrect.");
        }

        user.setPassword(newPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully.");
    }

}
