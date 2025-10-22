package com.starwars.starwars_fan.client;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.PlanetDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("it")
public class SwapiClientItTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private SwapiClient swapiClient;

    @BeforeEach
    void setUp() throws IOException {
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
    void shouldFetchPeopleFromMockApi() {
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

        List<PersonDto> people = swapiClient.fetchAllPeople();

        assertThat(people).isNotEmpty();
        assertThat(people.get(0).getName()).isEqualTo("Luke Skywalker");
        assertThat(people.get(0).getHeight()).isEqualTo("172");
        assertThat(people.get(0).getHomeworld()).contains("planets/1");
    }

    @Test
    void shouldFetchPlanetsFromMockApi() {
        String mockResponse = """
            {
              "results": [
                {
                  "name": "Yavin IV",
                  "climate": "tropical",
                  "terrain": "rainforest",
                  "url": "https://swapi.dev/api/planets/3/"
                }
              ]
            }
            """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        List<PlanetDto> planets = swapiClient.fetchAllPlanets();

        assertEquals(1, planets.size());
        assertEquals("Yavin IV", planets.get(0).getName());
        assertEquals("tropical", planets.get(0).getClimate());
    }
}
