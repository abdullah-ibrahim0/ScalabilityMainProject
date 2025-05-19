package com.example.SearchService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SearchServiceApplication {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Autowired
	private Environment env;

	@PostConstruct
	public void logRedisHost() {
		System.out.println("✅ Spring redis.host = " + redisHost); // or env.getProperty(...) if you want
	}

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}
}

