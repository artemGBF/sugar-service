package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UrlDto implements Serializable {

    private String href;
    private String  method;
    private String templated;
}
