package ru.gbf.sugar.sugar.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="sugar", uniqueConstraints= {@UniqueConstraint(columnNames = {"name", "color", "form"})})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Sugar implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String form;
    private Boolean synchronizationflag;
}
