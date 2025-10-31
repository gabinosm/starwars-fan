package com.starwars.starwars_fan.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;


@TestConfiguration
public class WireMockContainerConfig {

    private static final int WIREMOCK_INTERNAL_PORT = 8080;

    static GenericContainer<?> wiremockContainer;

    static {
        try {
            if (org.testcontainers.DockerClientFactory.instance().isDockerAvailable()) {
                wiremockContainer = new GenericContainer<>(DockerImageName.parse("wiremock/wiremock:3.9.1"))
                        .withExposedPorts(WIREMOCK_INTERNAL_PORT)
                        .withCopyFileToContainer(MountableFile.forClasspathResource("wiremock/mappings"), "/home/wiremock/mappings")
                        .withCopyFileToContainer(MountableFile.forClasspathResource("wiremock/__files"), "/home/wiremock/__files");
                wiremockContainer.start();
                System.out.println("WireMock (Testcontainers) start in port: " + wiremockContainer.getMappedPort(WIREMOCK_INTERNAL_PORT));
            } else {
                System.out.println("Docker no disponible — se usará WireMock externo o API real.");
            }
        } catch (Exception e) {
            System.out.println("No se pudo iniciar WireMockContainer, se usará la configuración existente: " + e.getMessage());
        }
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        String baseUrl;

        if (wiremockContainer != null && wiremockContainer.isRunning()) {
            baseUrl = "http://" + wiremockContainer.getHost() + ":" + wiremockContainer.getMappedPort(WIREMOCK_INTERNAL_PORT) + "/api/";
            System.out.println("🔌 Usando WireMock (Testcontainers): " + baseUrl);
        } else if (isWireMockExternalRunning("http://localhost:8081/__admin")) {
            baseUrl = "http://localhost:8081/api/";
            System.out.println("🔌 Usando WireMock externo (docker-compose): " + baseUrl);
        } else {
            baseUrl = "https://swapi.dev/api/";
            System.out.println("🌐 Usando API real: " + baseUrl);
        }

        registry.add("swapi.base-url", () -> baseUrl);
    }

    private static boolean isWireMockExternalRunning(String adminUrl) {
        try {
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net.URL(adminUrl).openConnection();
            conn.setConnectTimeout(500);
            conn.connect();
            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
