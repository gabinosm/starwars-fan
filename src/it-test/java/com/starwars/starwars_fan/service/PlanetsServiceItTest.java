package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "swapi.base-url=http://localhost:${mock.server.port}"
})
public class PlanetsServiceItTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private PlanetsService planetsService;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.setProperty("mock.server.port", String.valueOf(mockWebServer.getPort()));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldReturnPeopleFromSwapi() throws Exception {
        String json = """
            {
              "results": [
                { "name": "Tatooine" },
                { "name": "Naboou" }
              ]
            }
            """;
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(json)
                .addHeader("Content-Type", "application/json"));

        PagedResponse<PlanetDto> response = planetsService.getPlanets(1, 10, null, null);

        assertThat(response.getItems()).extracting(PlanetDto::getName)
                .containsExactly("Tatooine", "Naboou");
    }
}
