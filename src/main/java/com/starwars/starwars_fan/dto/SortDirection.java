package com.starwars.starwars_fan.dto;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection fromString(String value) {
        return value != null && value.equalsIgnoreCase("desc") ? DESC : ASC;
    }
}
