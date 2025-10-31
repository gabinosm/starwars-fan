package com.starwars.starwars_fan.unit.controller;

import com.starwars.starwars_fan.controller.PlanetsController;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PlanetDto;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PlanetsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanetsController.class)
public class PlanetsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PlanetsService planetsService;

    @Test
    void shouldReturnPlanetsList() throws Exception {

        PlanetDto tatooine = new PlanetDto();
        tatooine.setName("Tatooine");

        PagedResponse<PlanetDto> pagedResponse =
                new PagedResponse<>(1, 5, 1, 1, List.of(tatooine));

        Mockito.when(planetsService.getPlanets(anyInt(), anyInt(), anyString(), any(SortRequest.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/api/planets")
                        .param("page", "1")
                        .param("size", "5")
                        .param("search", "Tatooine")
                        .param("sortBy", "name")
                        .param("direction", "ASC")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].name").value("Tatooine"));
    }

    @Test
    void shouldHandleEmptyList() throws Exception {
        PagedResponse<PlanetDto> empty = new PagedResponse<>(1, 5, 0, 0, List.of());

        Mockito.when(planetsService.getPlanets(anyInt(), anyInt(), anyString(), any(SortRequest.class)))
                .thenReturn(empty);

        mockMvc.perform(get("/api/planets")
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").doesNotExist());
    }
}
