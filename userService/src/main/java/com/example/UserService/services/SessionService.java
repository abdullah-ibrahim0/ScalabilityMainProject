package com.example.UserService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

public class SessionService {

    private static SessionService instance;

    @Qualifier("sessionRedisTemplate")
    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    private SessionService() {}

    public static SessionService getInstance() {
        if (instance == null) {
            synchronized (SessionService.class) {
                if (instance == null) {
                    instance = new SessionService();
                }
            }
        }
        return instance;
    }

    // Setter for RedisTemplate — to be wired manually from Spring config
    public void setRedisTemplate(RedisTemplate<String, Long> template) {
        this.redisTemplate = template;
    }

    public void saveSession(String token, Long userId) {
        System.out.println("Saving session: " + token + " for userId: " + userId);
        redisTemplate.opsForValue().set(token, userId);
    }

    public void deleteSession(String token) {
        redisTemplate.delete(token);
    }

    public Long getUserIdFromToken(String token) {
        return redisTemplate.opsForValue().get(token);
    }
}
