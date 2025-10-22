package com.starwars.starwars_fan.controller;

import com.starwars.starwars_fan.dto.PagedResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class PeopleControllerItTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("swapi.base-url", () -> mockWebServer.url("/api/").toString());
    }

    @Test
    void shouldReturnPeopleList() {
        String mockResponse = """
            {
              "count": 1,
              "next": null,
              "previous": null,
              "results": [
                {
                  "name": "Luke Skywalker",
                  "height": "172",
                  "mass": "77",
                  "hair_color": "blond",
                  "skin_color": "fair",
                  "eye_color": "blue",
                  "birth_year": "19BBY",
                  "gender": "male",
                  "homeworld": "https://swapi.dev/api/planets/1/",
                  "url": "https://swapi.dev/api/people/1/"
                }
              ]
            }
            """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        String url = "http://localhost:" + port + "/api/people?page=1&size=5";

        ResponseEntity<PagedResponse> response = restTemplate.getForEntity(url, PagedResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getItems()).isNotEmpty();
    }
}
