package com.starwars.starwars_fan.client;

import com.starwars.starwars_fan.config.WireMockContainerConfig;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.PlanetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
@Import(WireMockContainerConfig.class)
public class SwapiClientIT {

    @Autowired
    private Environment env;

    @Autowired
    private SwapiClient swapiClient;

    @Test
    void shouldFetchPeopleFromMockApi() {
        System.out.println("Base URL: " + env.getProperty("swapi.base-url"));
        List<PersonDto> people = swapiClient.fetchAllPeople();

        assertThat(people).isNotEmpty();
        assertThat(people.get(0).getName()).isEqualTo("Luke Skywalker");
        assertThat(people.get(0).getHeight()).isEqualTo("172");
        assertThat(people.get(0).getHomeworld()).contains("planets/1");
    }

    @Test
    void shouldFetchPlanetsFromMockApi() {

        List<PlanetDto> planets = swapiClient.fetchAllPlanets();

        assertThat(planets).isNotEmpty();
        assertThat(planets.get(0).getName()).isEqualTo("Tatooine");
    }
}
