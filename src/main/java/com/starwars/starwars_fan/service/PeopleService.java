package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.sorting.SortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService extends BaseService<PersonDto> {

    private final SwapiClient swapiClient;
    private final SortStrategyFactory sortStrategyFactory;

    public PeopleService(SwapiClient swapiClient, SortStrategyFactory sortStrategyFactory) {
        this.swapiClient = swapiClient;
        this.sortStrategyFactory = sortStrategyFactory;
    }

    public PagedResponse<PersonDto> getPeople(int page, int size, String search, SortRequest sortRequest) {

        validatePaginationParams(page, size);

        List<PersonDto> allPeople = safeCall(swapiClient::fetchAllPeople,
                "Failed to fetch people from SWAPI");

        ensureNotEmpty(allPeople, "No people found in SWAPI");

        if (search != null && !search.isBlank()) {
            allPeople = allPeople.stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(search.toLowerCase()))
                    .toList();
            ensureNotEmpty(allPeople, "No people found matching search: " + search);
        }
        if (sortRequest != null && sortRequest.getSortBy() != null) {
            SortStrategy<PersonDto> strategy = sortStrategyFactory.getStrategy(sortRequest.getSortBy(), PersonDto.class);
            if (strategy != null) {
                allPeople = allPeople.stream()
                        .sorted(strategy.getComparator(sortRequest.getDirection()))
                        .toList();
            }
        }

        return paginate(allPeople, page, size);
    }
}
