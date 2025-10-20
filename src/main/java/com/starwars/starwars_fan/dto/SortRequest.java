package com.starwars.starwars_fan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SortRequest {
    private String sortBy;
    private SortDirection direction;
}
