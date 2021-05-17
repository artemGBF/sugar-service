package ru.gbf.sugar.sugar.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Token implements Serializable {

    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String token_type;
}
