package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
public class PlanetsServiceItTest {


    @Autowired
    private PlanetsService planetsService;

    @Test
    void shouldReturnPeopleFromSwapi() {

        PagedResponse<PlanetDto> response = planetsService.getPlanets(1, 10, null, null);

        assertThat(response.getItems()).extracting(PlanetDto::getName)
                .contains("Tatooine", "Naboo");
    }
}
