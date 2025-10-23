package com.starwars.starwars_fan.service;

import com.starwars.starwars_fan.dto.PagedResponse;
import com.starwars.starwars_fan.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("it")
public class PeopleServiceItTest {

    @Autowired
    private PeopleService peopleService;

    @Test
    void shouldReturnPeopleFromSwapi() {

        PagedResponse<PersonDto> response = peopleService.getPeople(1, 10, null, null);

        assertThat(response.getItems()).extracting(PersonDto::getName)
                .contains("Luke Skywalker", "Leia Organa");
    }
}
