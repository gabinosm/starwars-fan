package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortDirection;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatedSortStrategyTest {

    private final CreatedSortStrategy<PlanetDto> strategy = new CreatedSortStrategy<>(PlanetDto::getCreated);

    @Test
    void shouldSortByCreatedAscending() {
        PlanetDto older = new PlanetDto();
        older.setCreated("2014-12-09T13:50:51.644000Z");

        PlanetDto newer = new PlanetDto();
        newer.setCreated("2014-12-10T13:50:51.644000Z");

        List<PlanetDto> planets = Arrays.asList(newer, older);
        planets.sort(strategy.getComparator(SortDirection.ASC));

        assertThat(planets.get(0).getCreated()).isEqualTo(older.getCreated());
        assertThat(planets.get(1).getCreated()).isEqualTo(newer.getCreated());
    }

    @Test
    void shouldSortByCreatedDescending() {
        PlanetDto older = new PlanetDto();
        older.setCreated("2014-12-09T13:50:51.644000Z");

        PlanetDto newer = new PlanetDto();
        newer.setCreated("2014-12-10T13:50:51.644000Z");

        List<PlanetDto> planets = Arrays.asList(older, newer);
        planets.sort(strategy.getComparator(SortDirection.DESC));

        assertThat(planets.get(0).getCreated()).isEqualTo(newer.getCreated());
        assertThat(planets.get(1).getCreated()).isEqualTo(older.getCreated());
    }

    @Test
    void shouldReturnCorrectKey() {
        assertThat(strategy.getKey()).isEqualTo("created");
    }
}
