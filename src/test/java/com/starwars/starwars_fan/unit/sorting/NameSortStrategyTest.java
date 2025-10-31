package com.starwars.starwars_fan.unit.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.sorting.NameSortStrategy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NameSortStrategyTest {

    private final NameSortStrategy<PersonDto> strategy = new NameSortStrategy<>(PersonDto::getName);

    @Test
    void shouldSortByNameAscending() {
        PersonDto a = new PersonDto();
        a.setName("Anakin");

        PersonDto z = new PersonDto();
        z.setName("Zeb");

        List<PersonDto> sorted = Arrays.asList(z, a);
        sorted.sort(strategy.getComparator(SortDirection.ASC));

        assertThat(sorted.get(0).getName()).isEqualTo("Anakin");
        assertThat(sorted.get(1).getName()).isEqualTo("Zeb");
    }

    @Test
    void shouldSortByNameDescending() {
        PersonDto a = new PersonDto();
        a.setName("Anakin");

        PersonDto z = new PersonDto();
        z.setName("Zeb");

        List<PersonDto> sorted = Arrays.asList(a, z);
        sorted.sort(strategy.getComparator(SortDirection.DESC));

        assertThat(sorted.get(0).getName()).isEqualTo("Zeb");
        assertThat(sorted.get(1).getName()).isEqualTo("Anakin");
    }

    @Test
    void shouldReturnCorrectKey() {
        assertThat(strategy.getKey()).isEqualTo("name");
    }
}
