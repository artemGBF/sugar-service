package ru.gbf.sugar.sugar.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sugars")
public class Sugar implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String url1;
    private byte[] img1;
    private String url2;
    private byte[] img2;
    private boolean synchronizationFlag;
}
