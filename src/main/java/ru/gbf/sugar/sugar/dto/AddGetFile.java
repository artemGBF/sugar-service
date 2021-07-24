package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class AddGetFile implements Serializable {
    private String operation_id;
    private String href;
    private String  method;
    private Boolean templated;
}
