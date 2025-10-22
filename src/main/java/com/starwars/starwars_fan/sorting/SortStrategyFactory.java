package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.PlanetDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SortStrategyFactory {

    private final Map<String, SortStrategy<?>> strategies = new HashMap<>();

    public SortStrategyFactory() {
        strategies.put(key("name", PersonDto.class), new NameSortStrategy<PersonDto>(PersonDto::getName));
        strategies.put(key("created", PersonDto.class), new CreatedSortStrategy<PersonDto>(PersonDto::getCreated));

        strategies.put(key("name", PlanetDto.class), new NameSortStrategy<PlanetDto>(PlanetDto::getName));
        strategies.put(key("created", PlanetDto.class), new CreatedSortStrategy<PlanetDto>(PlanetDto::getCreated));
    }

    @SuppressWarnings("unchecked")
    public <T> SortStrategy<T> getStrategy(String sortBy, Class<T> clazz) {
        if (sortBy == null || clazz == null) return null;
        String key = key(sortBy, clazz);
        return (SortStrategy<T>) strategies.get(key);
    }

    private String key(String field, Class<?> clazz) {
        return field.toLowerCase() + "-" + clazz.getSimpleName().toLowerCase();
    }
}
