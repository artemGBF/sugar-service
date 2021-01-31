package ru.gbf.sugar.sugar.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("sugars")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Sugar {

    private Long id;
    private String name;
    private String color;
    private String url1;
    private String url2;
    private Boolean synchronizationFlag;
    private String description;
}
