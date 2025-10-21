package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.SortDirection;

import java.util.Comparator;
import java.util.List;

public interface SortStrategy<T> {
    String getKey();
    Comparator<T> getComparator(SortDirection direction);
}
