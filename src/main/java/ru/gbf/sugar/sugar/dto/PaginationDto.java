package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class PaginationDto implements Serializable {
    private int pageSize;
    private int total;
    private ArrayList<String> sugarSet;
}
