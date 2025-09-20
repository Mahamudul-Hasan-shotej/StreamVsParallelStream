package com.example.stream.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FruitControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testStreamVsParallelStreamEndpoint() {
        String url = "http://localhost:" + port + "/api/fruits/test-stream";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("Response: " + response.getBody());
    }
}
