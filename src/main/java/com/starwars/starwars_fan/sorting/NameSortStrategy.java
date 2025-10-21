package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.SortDirection;

import java.util.Comparator;
import java.util.function.Function;

public class NameSortStrategy<T> implements SortStrategy<T> {

    private final Function<T, String> nameExtractor;

    public NameSortStrategy(java.util.function.Function<T, String> nameExtractor) {
        this.nameExtractor = nameExtractor;
    }

    @Override
    public String getKey() {
        return "name";
    }

    @Override
    public Comparator<T> getComparator(SortDirection direction) {
        Comparator<T> comp = Comparator.comparing(nameExtractor, String.CASE_INSENSITIVE_ORDER);
        return direction == SortDirection.DESC ? comp.reversed() : comp;
    }
}
