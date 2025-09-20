package com.example.stream.controller;

import com.example.stream.service.FruitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/test-stream")
    public String testStream() {
        fruitService.testStreamVsParallelStream();
        return "Stream test completed. Check server logs for results.";
    }
}
