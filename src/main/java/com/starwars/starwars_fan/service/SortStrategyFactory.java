package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.sorting.CreatedSortStrategy;
import com.starwars.starwars_fan.sorting.NameSortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SortStrategyFactory {

    private final Map<String, SortStrategy<?>> strategies = new HashMap<>();

    public SortStrategyFactory(
            NameSortStrategy nameStrategy,
            CreatedSortStrategy createdStrategy
    ) {
        strategies.put("name", nameStrategy);
        strategies.put("created", createdStrategy);
    }

    @SuppressWarnings("unchecked")
    public <T> SortStrategy<T> getStrategy(String key) {
        return (SortStrategy<T>) strategies.getOrDefault(key, strategies.get("name"));
    }
}
