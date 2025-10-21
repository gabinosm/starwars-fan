package com.starwars.starwars_fan.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonResponse {
    private int count;
    private String next;
    private String previous;
    private List<PersonDto> results;
}
