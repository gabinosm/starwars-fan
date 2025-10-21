package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortStrategyTest {

    @Test
    void testNameSortStrategyAscending() {
        NameSortStrategy<PersonDto> strategy = new NameSortStrategy<>(PersonDto::getName);

        PersonDto a = new PersonDto(); a.setName("Anakin");
        PersonDto b = new PersonDto(); b.setName("Luke");

        List<PersonDto> sorted = List.of(b, a).stream()
                .sorted(strategy.getComparator(SortDirection.ASC))
                .toList();

        assertEquals("Anakin", sorted.get(0).getName());
    }

    @Test
    void testNameSortStrategyDescending() {
        NameSortStrategy<PersonDto> strategy = new NameSortStrategy<>(PersonDto::getName);

        PersonDto a = new PersonDto(); a.setName("Anakin");
        PersonDto b = new PersonDto(); b.setName("Luke");

        List<PersonDto> sorted = List.of(a, b).stream()
                .sorted(strategy.getComparator(SortDirection.DESC))
                .toList();

        assertEquals("Luke", sorted.get(0).getName());
    }
}
