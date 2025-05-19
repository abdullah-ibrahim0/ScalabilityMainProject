package com.merchant.merchant_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testEndpoint(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return "Authenticated userId: " + userId;
    }
}
