package com.starwars.starwars_fan.controller;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.dto.SortRequest;
import com.starwars.starwars_fan.service.PeopleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeopleController.class)
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @Test
    void shouldReturnPeopleList() throws Exception {

        PersonDto luke = new PersonDto();
        luke.setName("Luke Skywalker");

        PagedResponse<PersonDto> pagedResponse =
                new PagedResponse<>(1, 5, 1, 1, List.of(luke));

        Mockito.when(peopleService.getPeople(anyInt(), anyInt(), anyString(), any(SortRequest.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/api/people")
                        .param("page", "1")
                        .param("size", "5")
                        .param("search", "Luke")
                        .param("sortBy", "name")
                        .param("direction", "ASC")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("Luke Skywalker"));
    }

    @Test
    void shouldHandleEmptyList() throws Exception {
        PagedResponse<PersonDto> pagedResponse = new PagedResponse<>(1, 5, 0, 0, new ArrayList<>());

        Mockito.when(peopleService.getPeople(anyInt(), anyInt(), anyString(), any(SortRequest.class)))
                .thenReturn(pagedResponse);

        mockMvc.perform(get("/api/people")
                        .param("page", "1")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").doesNotExist());
    }
}
