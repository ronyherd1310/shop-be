package com.example.shopbe.infrastructure.controller;

import com.example.shopbe.domain.entity.Greeting;
import com.example.shopbe.domain.usecase.GetGreetingUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GetGreetingUseCase getGreetingUseCase;

    public GreetingController(GetGreetingUseCase getGreetingUseCase) {
        this.getGreetingUseCase = getGreetingUseCase;
    }

    @GetMapping("/hello")
    public Greeting greeting(@RequestParam(value = "name", required = false) String name) {
        return getGreetingUseCase.getGreeting(name);
    }
}