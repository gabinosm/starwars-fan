package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanetsService {

    private final SwapiClient swapiClient;
    private final SortStrategyFactory sortStrategyFactory;

    public PlanetsService(SwapiClient swapiClient, SortStrategyFactory sortStrategyFactory) {
        this.swapiClient = swapiClient;
        this.sortStrategyFactory = sortStrategyFactory;
    }

    public PagedResponse<PlanetDto> getPlanets(int page, int size, String search, SortRequest sortRequest) {
        List<PlanetDto> allPlanets = swapiClient.fetchAllPlanets();
        // TODO: filtrar, ordenar y paginar
        return new PagedResponse<>(page, size, allPlanets.size(), 1, allPlanets);
    }
}
