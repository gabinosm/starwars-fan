package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.client.SwapiClient;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

    private final SwapiClient swapiClient;
    private final SortStrategyFactory sortStrategyFactory;

    public PeopleService(SwapiClient swapiClient, SortStrategyFactory sortStrategyFactory) {
        this.swapiClient = swapiClient;
        this.sortStrategyFactory = sortStrategyFactory;
    }

    public PagedResponse<PersonDto> getPeople(int page, int size, String search, SortRequest sortRequest) {
        List<PersonDto> allPeople = swapiClient.fetchAllPeople();
        // TODO: filtrar, ordenar y paginar
        return new PagedResponse<>(page, size, allPeople.size(), 1, allPeople);
    }
}
