package ru.gbf.sugar.sugar.mapper;

import org.springframework.stereotype.Component;
import ru.gbf.sugar.sugar.dto.SugarDTO;
import ru.gbf.sugar.sugar.entity.Sugar;

@Component
public class SugarMapper {

    public SugarDTO toDTO(Sugar sugar) {
        return new SugarDTO(
                sugar.getId(),
                sugar.getName(),
                sugar.getColor(),
                sugar.getForm(),
                sugar.getImg1(),
                sugar.getImg2(),
                sugar.getSync(),
                sugar.getActive()
        );
    }

    public Sugar toEntity(SugarDTO sugar) {
        return new Sugar(
                sugar.getId(),
                sugar.getName(),
                sugar.getColor(),
                sugar.getForm(),
                sugar.getImg1(),
                sugar.getImg2(),
                sugar.getSync(),
                sugar.getActive()
        );
    }
}
