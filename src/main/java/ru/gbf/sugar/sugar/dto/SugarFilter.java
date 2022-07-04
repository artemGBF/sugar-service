package ru.gbf.sugar.sugar.dto;

import lombok.Data;
import ru.gbf.sugar.sugar.entity.SugarForm;

@Data
public class SugarFilter {
    private String name;
    private String color;
    private SugarForm form;

    private PaginationDTO pagination;
}
