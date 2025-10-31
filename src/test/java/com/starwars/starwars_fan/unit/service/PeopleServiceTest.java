package com.starwars.starwars_fan.unit.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PeopleService;
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

public class PeopleServiceTest {

    @Mock
    private SwapiClient swapiClient;
    @Mock
    private SortStrategyFactory sortStrategyFactory;
    @Mock
    private SortStrategy<PersonDto> sortStrategy;

    @InjectMocks
    private PeopleService peopleService;

    private List<PersonDto> mockPeople;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PersonDto luke = new PersonDto(); luke.setName("Luke");
        PersonDto leia = new PersonDto(); leia.setName("Leia");
        PersonDto han = new PersonDto(); han.setName("Han");
        mockPeople = List.of(luke, leia, han);
    }

    @Test
    void shouldFilterPeopleBySearch() {
        when(swapiClient.fetchAllPeople()).thenReturn(mockPeople);

        PagedResponse<PersonDto> result = peopleService.getPeople(1, 10, "le", null);

        assertThat(result.getItems()).extracting(PersonDto::getName)
                .containsExactly("Leia");
    }

    @Test
    void shouldSortPeopleAscending() {
        when(swapiClient.fetchAllPeople()).thenReturn(mockPeople);
        when(sortStrategyFactory.getStrategy(eq("name"), eq(PersonDto.class))).thenReturn(sortStrategy);
        when(sortStrategy.getComparator(SortDirection.ASC))
                .thenReturn(Comparator.comparing(PersonDto::getName));

        SortRequest sort = new SortRequest("name", SortDirection.ASC);
        PagedResponse<PersonDto> result = peopleService.getPeople(1, 10, null, sort);

        assertThat(result.getItems().getFirst().getName()).isEqualTo("Han");
        assertThat(result.getItems().getLast().getName()).isEqualTo("Luke");
    }

    @Test
    void shouldPaginateCorrectly() {
        when(swapiClient.fetchAllPeople()).thenReturn(mockPeople);

        PagedResponse<PersonDto> result = peopleService.getPeople(2, 1, null, null);

        assertThat(result.getPage()).isEqualTo(2);
        assertThat(result.getItems()).hasSize(1);
        assertThat(result.getTotalPages()).isEqualTo(3);
    }
}
