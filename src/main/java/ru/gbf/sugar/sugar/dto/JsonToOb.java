package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class JsonToOb implements Serializable {

    private String[] jsonArr;
}
