package com.example.UserService.services.login;

import com.example.UserService.models.User;
import com.example.UserService.repositories.UserRepository;
import com.example.UserService.services.JwtService;
import com.example.UserService.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("basic")
public class BasicLoginStrategy implements LoginStrategy {

    @Autowired
    private UserRepository userRepository;
    private final JwtService jwtService = JwtService.getInstance();

    @Override
    public String login(String email, String password) {
        SessionService sessionService = SessionService.getInstance();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }


        if (!user.isEmailVerified() || !user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUserId(), user.getEmail());
        sessionService.saveSession(token, user.getUserId());
        return token;
    }
}
