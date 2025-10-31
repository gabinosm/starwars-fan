package com.starwars.starwars_fan.integration.service;

import com.starwars.starwars_fan.config.WireMockContainerConfig;
import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import com.starwars.starwars_fan.service.PeopleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = WireMockContainerConfig.class)
@ActiveProfiles("it")
public class PeopleServiceIT {

    @Autowired
    private PeopleService peopleService;

    @Test
    void shouldReturnPeopleFromSwapi() {

        PagedResponse<PersonDto> response = peopleService.getPeople(1, 10, null, null);

        assertThat(response.getItems()).extracting(PersonDto::getName)
                .contains("Luke Skywalker", "Leia Organa");
    }
}
