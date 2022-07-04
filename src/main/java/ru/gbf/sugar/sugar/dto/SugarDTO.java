package ru.gbf.sugar.sugar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gbf.sugar.sugar.entity.SugarForm;

@Data
@AllArgsConstructor
public class SugarDTO {
    private Long id;
    private String name;
    private String color;
    private SugarForm form;
    private byte[] img1;
    private byte[] img2;
    private Boolean sync;
    private Boolean active;
}