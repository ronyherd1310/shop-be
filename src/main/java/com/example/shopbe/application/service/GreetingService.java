package com.example.shopbe.application.service;

import com.example.shopbe.domain.entity.Greeting;
import com.example.shopbe.domain.usecase.GetGreetingUseCase;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements GetGreetingUseCase {

    @Override
    public Greeting getGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new Greeting("Hello, World!");
        }
        return new Greeting("Hello, " + name + "!");
    }
}