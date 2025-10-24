package com.starwars.starwars_fan.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;


@TestConfiguration
public class WireMockContainerConfig {

    private static final GenericContainer<?> wiremockContainer =
            new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:3.9.1"))
                    .withExposedPorts(8080)
                    .withClasspathResourceMapping(
                            "wiremock",
                            "/home/wiremock",
                            org.testcontainers.containers.BindMode.READ_ONLY
                    );

    static {
        wiremockContainer.start();
        System.out.println("🚀 WireMock iniciado en puerto: " + wiremockContainer.getMappedPort(8080));
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        String baseUrl = "http://" + wiremockContainer.getHost() + ":" + wiremockContainer.getMappedPort(8080);
        registry.add("swapi.base-url", () -> baseUrl + "/api/");
    }
}
