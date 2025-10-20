package com.starwars.starwars_fan.sorting;

import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Comparator;

@Component
public class CreatedSortStrategy implements SortStrategy<PersonDto> {

    @Override
    public String getKey() {
        return "created";
    }

    @Override
    public Comparator<PersonDto> getComparator(SortDirection direction) {
        Comparator<PersonDto> comp = Comparator.comparing(p ->
                OffsetDateTime.parse(p.getCreated())
        );
        return direction == SortDirection.DESC ? comp.reversed() : comp;
    }
}
