package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class NameSortStrategy implements SortStrategy<PersonDto> {

    @Override
    public String getKey() {
        return "name";
    }

    @Override
    public Comparator<PersonDto> getComparator(SortDirection direction) {
        Comparator<PersonDto> comp = Comparator.comparing(PersonDto::getName, String.CASE_INSENSITIVE_ORDER);
        return direction == SortDirection.DESC ? comp.reversed() : comp;
    }
}
