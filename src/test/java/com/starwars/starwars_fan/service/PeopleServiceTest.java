package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
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

public class PeopleServiceTest {

    private SwapiClient swapiClient;
    private SortStrategyFactory sortStrategyFactory;
    private PeopleService peopleService;

    @BeforeEach
    void setUp() {
        swapiClient = mock(SwapiClient.class);
        sortStrategyFactory = mock(SortStrategyFactory.class);
        peopleService = new PeopleService(swapiClient, sortStrategyFactory);
    }

    @Test
    void shouldFilterPeopleBySearchTerm() {
        PersonDto luke = new PersonDto(); luke.setName("Luke Skywalker");
        PersonDto leia = new PersonDto(); leia.setName("Leia Organa");
        when(swapiClient.fetchAllPeople()).thenReturn(List.of(luke, leia));

        PagedResponse<PersonDto> response = peopleService.getPeople(1, 15, "luke", null);

        assertEquals(1, response.getItems().size());
        assertEquals("Luke Skywalker", response.getItems().get(0).getName());
    }

    @Test
    void shouldSortPeopleByNameDescending() {
        PersonDto luke = new PersonDto(); luke.setName("Luke");
        PersonDto anakin = new PersonDto(); anakin.setName("Anakin");

        SortStrategy<PersonDto> mockStrategy = mock(SortStrategy.class);
        when(mockStrategy.getComparator(SortDirection.DESC))
                .thenReturn(Comparator.comparing(PersonDto::getName).reversed());
        when(sortStrategyFactory.getStrategy("name", PersonDto.class)).thenReturn(mockStrategy);
        when(swapiClient.fetchAllPeople()).thenReturn(List.of(luke, anakin));

        SortRequest sortRequest = new SortRequest("name", SortDirection.DESC);
        PagedResponse<PersonDto> response = peopleService.getPeople(1, 15, null, sortRequest);

        assertEquals("Luke", response.getItems().get(0).getName());
    }

    @Test
    void shouldPaginateCorrectly() {
        List<PersonDto> people = List.of(
                new PersonDto(), new PersonDto(), new PersonDto()
        );
        when(swapiClient.fetchAllPeople()).thenReturn(people);

        PagedResponse<PersonDto> response = peopleService.getPeople(2, 2, null, null);

        assertEquals(2, response.getPage());
        assertEquals(2, response.getSize());
        assertEquals(3, response.getTotalItems());
    }
}
