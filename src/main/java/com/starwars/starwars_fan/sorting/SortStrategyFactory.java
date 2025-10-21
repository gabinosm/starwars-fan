package com.starwars.starwars_fan.sorting;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SortStrategyFactory {

    private final Map<String, SortStrategy<?>> strategies = new HashMap<>();

    public SortStrategyFactory() {
    }

    public void register(SortStrategy<?> strategy, Class<?> type) {
        String key = buildKey(strategy.getKey(), type);
        strategies.put(key, strategy);
    }

    @SuppressWarnings("unchecked")
    public <T> SortStrategy<T> getStrategy(String field, Class<T> type) {
        if (field == null || type == null) return null;
        String key = buildKey(field, type);
        return (SortStrategy<T>) strategies.get(key);
    }

    private String buildKey(String field, Class<?> type) {
        return field.toLowerCase() + ":" + type.getSimpleName().toLowerCase().replace("dto", "");
    }
}
