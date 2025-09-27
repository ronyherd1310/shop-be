package com.example.shopbe.domain.usecase;

import com.example.shopbe.domain.entity.Greeting;

public interface GetGreetingUseCase {
    Greeting getGreeting(String name);
}