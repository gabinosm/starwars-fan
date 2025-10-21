package com.starwars.starwars_fan.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlanetResponse {
    private int count;
    private String next;
    private String previous;
    private List<PlanetDto> results;
}
