package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.SortDirection;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.function.Function;

public class CreatedSortStrategy<T> implements SortStrategy<T> {

    private final Function<T, String> createdExtractor;

    public CreatedSortStrategy(Function<T, String> createdExtractor) {
        this.createdExtractor = createdExtractor;
    }

    @Override
    public String getKey() {
        return "created";
    }

    @Override
    public Comparator<T> getComparator(SortDirection direction) {
        Comparator<T> comparator = Comparator.comparing(
                item -> parseDate(createdExtractor.apply(item)),
                Comparator.nullsLast(Comparator.naturalOrder())
        );
        return direction == SortDirection.DESC ? comparator.reversed() : comparator;
    }

    private OffsetDateTime parseDate(String date) {
        try {
            return date != null ? OffsetDateTime.parse(date) : null;
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
