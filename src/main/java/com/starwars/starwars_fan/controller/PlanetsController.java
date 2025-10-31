package com.starwars.starwars_fan.controller;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PlanetsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planets")
@Tag(name = "People", description = "Endpoints to get Star Wars planets")
public class PlanetsController {

    private final PlanetsService planetsService;

    public PlanetsController(PlanetsService planetsService) {
        this.planetsService = planetsService;
    }

    @GetMapping
    @Operation(summary = "Get Planets list",
            description = "Allows you to search, sort, and paginate results.")
    public PagedResponse<PlanetDto> getPlanets(
            @Parameter(description = "Number page (start in 1)")
            @RequestParam(defaultValue = "1") int page,

            @Parameter(description = "Size of page")
            @RequestParam(defaultValue = "15") int size,

            @Parameter(description = "Filter of search")
            @RequestParam(required = false) String search,

            @Parameter(description = "Field to sort by")
            @RequestParam(required = false) String sortBy,

            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "asc") String direction
    ) {
        SortRequest sortRequest = new SortRequest(sortBy, SortDirection.fromString(direction));
        return planetsService.getPlanets(page, size, search, sortRequest);
    }
}
