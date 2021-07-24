package ru.gbf.sugar.sugar.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class JsonAdd implements Serializable {

    private String color;
    private String form;
    private String place;
    private String name;
    private String date;
}
