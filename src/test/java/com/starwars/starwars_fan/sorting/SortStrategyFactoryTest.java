package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SortStrategyFactoryTest {

    private SortStrategyFactory factory;

    @BeforeEach
    void setup() {
        factory = new SortStrategyFactory();
    }

    @Test
    void shouldReturnNameSortStrategy() {
        SortStrategy<PersonDto> strategy = factory.getStrategy("name", PersonDto.class);
        assertThat(strategy).isInstanceOf(NameSortStrategy.class);
        assertThat(strategy.getKey()).isEqualTo("name");
    }

    @Test
    void shouldReturnCreatedSortStrategy() {
        SortStrategy<PersonDto> strategy = factory.getStrategy("created", PersonDto.class);
        assertThat(strategy).isInstanceOf(CreatedSortStrategy.class);
        assertThat(strategy.getKey()).isEqualTo("created");
    }

    @Test
    void shouldReturnNullForUnknownField() {
        SortStrategy<PersonDto> strategy = factory.getStrategy("mass", PersonDto.class);
        assertThat(strategy).isNull();
    }
}
