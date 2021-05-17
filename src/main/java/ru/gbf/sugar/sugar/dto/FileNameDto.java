package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class FileNameDto implements Serializable {

    private String[] items;



}
