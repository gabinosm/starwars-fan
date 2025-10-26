package com.starwars.starwars_fan.controller;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortDirection;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/people")
@Tag(name = "People", description = "Endpoints to get Star Wars people")
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    @Operation(summary = "Get People list",
            description = "Allows you to search, sort, and paginate results.")
    public ResponseEntity<PagedResponse<PersonDto>> getPeople(
            @Parameter(description = "Numer page (start in 1)")
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
        return ResponseEntity.ok(
                peopleService.getPeople(page, size, search, sortRequest)
        );
    }
}
