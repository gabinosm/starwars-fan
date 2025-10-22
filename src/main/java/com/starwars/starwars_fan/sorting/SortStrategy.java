package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.SortDirection;

import java.util.Comparator;

public interface SortStrategy<T> {
    String getKey();
    Comparator<T> getComparator(SortDirection direction);
}
