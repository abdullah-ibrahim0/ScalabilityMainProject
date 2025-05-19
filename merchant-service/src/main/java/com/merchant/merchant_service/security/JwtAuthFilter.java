package com.merchant.merchant_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secretKey}")
    private String secretKey;

    private byte[] decodedKey;

    @Override
    public void afterPropertiesSet() throws ServletException {
        decodedKey = Decoders.BASE64.decode(secretKey);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestUri = request.getServletPath();

        if (requestUri.matches(".*/swagger-ui.*") ||
                requestUri.matches(".*/v3/api-docs.*") ||
                requestUri.matches(".*/auth/login.*") ||
                requestUri.equals("/products/all")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(response, "Missing or invalid Authorization header");
            return;
        }

        String jwt = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(decodedKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.before(new Date())) {
                sendUnauthorized(response, "Token expired");
                return;
            }

            String userId = claims.getSubject();
            request.setAttribute("userId", userId);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            System.err.println("Error parsing JWT: " + e.getMessage());
            sendUnauthorized(response, "Invalid token: " + e.getMessage());
        }
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"error\": \"Unauthorized\", \"message\": \"%s\"}", message)
        );
    }
}
