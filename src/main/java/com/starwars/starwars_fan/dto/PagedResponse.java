package com.starwars.starwars_fan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private List<T> items = new ArrayList<>();
}
