package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.sorting.SortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlanetsServiceTest {

    private SwapiClient swapiClient;
    private SortStrategyFactory sortStrategyFactory;
    private PlanetsService planetsService;

    @BeforeEach
    void setUp() {
        swapiClient = mock(SwapiClient.class);
        sortStrategyFactory = mock(SortStrategyFactory.class);
        planetsService = new PlanetsService(swapiClient, sortStrategyFactory);
    }

    @Test
    void shouldFilterPlanetsByName() {
        PlanetDto tatooine = new PlanetDto(); tatooine.setName("Tatooine");
        PlanetDto naboo = new PlanetDto(); naboo.setName("Naboo");
        when(swapiClient.fetchAllPlanets()).thenReturn(List.of(tatooine, naboo));

        PagedResponse<PlanetDto> response = planetsService.getPlanets(1, 15, "tat", null);

        assertEquals(1, response.getItems().size());
        assertEquals("Tatooine", response.getItems().get(0).getName());
    }

    @Test
    void shouldSortPlanetsByNameAscending() {
        PlanetDto yavin = new PlanetDto(); yavin.setName("Yavin");
        PlanetDto alderaan = new PlanetDto(); alderaan.setName("Alderaan");

        SortStrategy<PlanetDto> mockStrategy = mock(SortStrategy.class);
        when(mockStrategy.getComparator(SortDirection.ASC))
                .thenReturn(Comparator.comparing(PlanetDto::getName));
        when(sortStrategyFactory.getStrategy("name", PlanetDto.class)).thenReturn(mockStrategy);
        when(swapiClient.fetchAllPlanets()).thenReturn(List.of(yavin, alderaan));

        SortRequest sortRequest = new SortRequest("name", SortDirection.ASC);
        PagedResponse<PlanetDto> response = planetsService.getPlanets(1, 15, null, sortRequest);

        assertEquals("Alderaan", response.getItems().get(0).getName());
    }
}
