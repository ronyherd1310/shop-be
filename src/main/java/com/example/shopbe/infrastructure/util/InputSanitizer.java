package com.example.shopbe.infrastructure.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class InputSanitizer {

    private static final Pattern MALICIOUS_PATTERN = Pattern.compile("[<>\"'%;()&+\\\\]");

    /**
     * Sanitizes input string by removing potentially malicious characters
     * @param input The string to sanitize
     * @return Sanitized string or null if input is null
     */
    public String sanitize(String input) {
        if (input == null) {
            return null;
        }

        // Remove potentially malicious characters
        String sanitized = MALICIOUS_PATTERN.matcher(input).replaceAll("");

        // Trim whitespace
        sanitized = sanitized.trim();

        // Limit length to prevent DoS
        if (sanitized.length() > 500) {
            sanitized = sanitized.substring(0, 500);
        }

        return sanitized;
    }

    /**
     * Sanitizes email input with more permissive rules (allows @ and .)
     * @param email The email string to sanitize
     * @return Sanitized email or null if input is null
     */
    public String sanitizeEmail(String email) {
        if (email == null) {
            return null;
        }

        // More restrictive pattern for emails, only allows alphanumeric, @, ., -, _
        Pattern emailPattern = Pattern.compile("[^a-zA-Z0-9@.\\-_]");
        String sanitized = emailPattern.matcher(email).replaceAll("");

        sanitized = sanitized.trim();

        if (sanitized.length() > 100) {
            sanitized = sanitized.substring(0, 100);
        }

        return sanitized;
    }
}
