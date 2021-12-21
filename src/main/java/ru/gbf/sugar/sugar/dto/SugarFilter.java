package ru.gbf.sugar.sugar.dto;

import lombok.Data;

@Data
public class SugarFilter {
    private String name;
    private String color;

    private int size;
    private int page;
}
