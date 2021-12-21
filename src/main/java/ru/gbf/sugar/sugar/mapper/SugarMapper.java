package ru.gbf.sugar.sugar.mapper;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.gbf.sugar.sugar.dto.SugarDTO;
import ru.gbf.sugar.sugar.entity.Sugar;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class SugarMapper {

    @SneakyThrows(IOException.class)
    public SugarDTO toDTO(Sugar sugar) {
        return new SugarDTO(
                sugar.getId(),
                sugar.getName(),
                sugar.getColor(),
                sugar.getUrl1(),
                ImageIO.read(new ByteArrayInputStream(sugar.getImg1())),
                sugar.isSync()
        );
    }
}
