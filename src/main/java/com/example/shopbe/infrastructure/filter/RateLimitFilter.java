package com.example.shopbe.infrastructure.filter;

import com.example.shopbe.infrastructure.config.RateLimitConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Bucket> rateLimitBucketCache;
    private final RateLimitConfig rateLimitConfig;
    private final ObjectMapper objectMapper;

    public RateLimitFilter(Map<String, Bucket> rateLimitBucketCache,
                          RateLimitConfig rateLimitConfig,
                          ObjectMapper objectMapper) {
        this.rateLimitBucketCache = rateLimitBucketCache;
        this.rateLimitConfig = rateLimitConfig;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Only apply rate limiting to /api/orders endpoints
        String requestPath = httpRequest.getRequestURI();
        if (!requestPath.startsWith("/api/orders")) {
            chain.doFilter(request, response);
            return;
        }

        // Use IP address as identifier for rate limiting
        String clientIp = getClientIP(httpRequest);

        Bucket bucket = rateLimitBucketCache.computeIfAbsent(clientIp,
            k -> rateLimitConfig.createBucket());

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(429);
            httpResponse.setContentType("application/json");

            Map<String, String> errorResponse = Map.of(
                "error", "Too Many Requests",
                "message", "Rate limit exceeded. Please try again later."
            );

            httpResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
