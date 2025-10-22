package com.starwars.starwars_fan.config;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.sorting.CreatedSortStrategy;
import com.starwars.starwars_fan.sorting.NameSortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategyFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SortStrategyConfig {
    /*private final SortStrategyFactory sortStrategyFactory;

    public SortStrategyConfig(SortStrategyFactory sortStrategyFactory) {
        this.sortStrategyFactory = sortStrategyFactory;
    }

    @PostConstruct
    public void registerStrategies() {
        sortStrategyFactory.register(new NameSortStrategy<>(PersonDto::getName), PersonDto.class);
        sortStrategyFactory.register(new CreatedSortStrategy<>(PersonDto::getCreated), PersonDto.class);

        sortStrategyFactory.register(new NameSortStrategy<>(PlanetDto::getName), PlanetDto.class);
        sortStrategyFactory.register(new CreatedSortStrategy<>(PlanetDto::getCreated), PlanetDto.class);
    }*/
}
