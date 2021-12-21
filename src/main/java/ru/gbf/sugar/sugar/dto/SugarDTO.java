package ru.gbf.sugar.sugar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;

@Data
@AllArgsConstructor
public class SugarDTO {
    private Long id;
    private String name;
    private String color;
    private String url1;
    private BufferedImage img1;
    private boolean sync;
}