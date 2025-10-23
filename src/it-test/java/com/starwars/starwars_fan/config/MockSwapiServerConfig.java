package com.starwars.starwars_fan.config;

import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;

@TestConfiguration
public class MockSwapiServerConfig {
    private static final MockWebServer mockWebServer;

    static {
        try {
            mockWebServer = new MockWebServer();
            mockWebServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(destroyMethod = "shutdown")
    public MockWebServer mockWebServer() {
        return mockWebServer;
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("swapi.base-url", () -> mockWebServer.url("/api/").toString());
    }

}
