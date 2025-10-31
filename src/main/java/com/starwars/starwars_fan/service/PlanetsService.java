package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.sorting.SortStrategy;
import com.starwars.starwars_fan.sorting.SortStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetsService extends BaseService<PlanetDto> {

    private final SwapiClient swapiClient;
    private final SortStrategyFactory sortStrategyFactory;

    public PlanetsService(SwapiClient swapiClient, SortStrategyFactory sortStrategyFactory) {
        this.swapiClient = swapiClient;
        this.sortStrategyFactory = sortStrategyFactory;
    }

    public PagedResponse<PlanetDto> getPlanets(int page, int size, String search, SortRequest sortRequest) {

        validatePaginationParams(page, size);

        List<PlanetDto> allPlanets = safeCall(swapiClient::fetchAllPlanets,
                "Failed to fetch planets from SWAPI");

        ensureNotEmpty(allPlanets, "No planets found in SWAPI");

        if (search != null && !search.isBlank()) {
            allPlanets = allPlanets.stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(search.toLowerCase()))
                    .toList();
            ensureNotEmpty(allPlanets, "No planets found matching search: " + search);
        }

        if (sortRequest != null && sortRequest.getSortBy() != null) {
            SortStrategy<PlanetDto> strategy = sortStrategyFactory.getStrategy(sortRequest.getSortBy(), PlanetDto.class);
            if (strategy != null) {
                allPlanets = allPlanets.stream()
                        .sorted(strategy.getComparator(sortRequest.getDirection()))
                        .toList();
            }
        }

        return paginate(allPlanets, page, size);
    }
}
