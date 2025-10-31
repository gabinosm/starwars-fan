package com.starwars.starwars_fan.unit.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PlanetsService;
import com.starwars.starwars_fan.sorting.SortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class PlanetsServiceTest {

    @Mock
    private SwapiClient swapiClient;
    @Mock
    private SortStrategyFactory sortStrategyFactory;
    @Mock
    private SortStrategy<PlanetDto> sortStrategy;

    @InjectMocks
    private PlanetsService planetsService;

    private List<PlanetDto> mockPlanets;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PlanetDto tatooine = new PlanetDto(); tatooine.setName("Tatooine");
        PlanetDto naboo = new PlanetDto(); naboo.setName("Naboo");
        PlanetDto hoth = new PlanetDto(); hoth.setName("Hoth");
        mockPlanets = List.of(tatooine, naboo, hoth);
    }

    @Test
    void shouldFilterPlanetsBySearch() {
        when(swapiClient.fetchAllPlanets()).thenReturn(mockPlanets);

        PagedResponse<PlanetDto> result = planetsService.getPlanets(1, 10, "tat", null);

        assertThat(result.getItems()).extracting(PlanetDto::getName)
                .containsExactly("Tatooine");
    }

    @Test
    void shouldSortPlanetsDescending() {
        when(swapiClient.fetchAllPlanets()).thenReturn(mockPlanets);
        when(sortStrategyFactory.getStrategy(eq("name"), eq(PlanetDto.class))).thenReturn(sortStrategy);
        when(sortStrategy.getComparator(SortDirection.DESC))
                .thenReturn(Comparator.comparing(PlanetDto::getName).reversed());

        SortRequest sort = new SortRequest("name", SortDirection.DESC);
        PagedResponse<PlanetDto> result = planetsService.getPlanets(1, 10, null, sort);

        assertThat(result.getItems().getFirst().getName()).isEqualTo("Tatooine");
    }

    @Test
    void shouldPaginateCorrectly() {
        when(swapiClient.fetchAllPlanets()).thenReturn(mockPlanets);

        PagedResponse<PlanetDto> result = planetsService.getPlanets(2, 1, null, null);

        assertThat(result.getPage()).isEqualTo(2);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getTotalPages()).isEqualTo(3);
    }
}
