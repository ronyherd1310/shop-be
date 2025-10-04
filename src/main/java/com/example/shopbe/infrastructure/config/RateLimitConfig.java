package com.example.shopbe.infrastructure.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfig {

    /**
     * Creates a bucket cache for rate limiting per customer email
     * Each customer is limited to 10 requests per minute
     */
    @Bean
    public Map<String, Bucket> rateLimitBucketCache() {
        return new ConcurrentHashMap<>();
    }

    /**
     * Creates a new rate limit bucket for a customer
     * Allows 10 requests per minute
     */
    public Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
