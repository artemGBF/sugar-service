package ru.gbf.sugar.sugar.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gbf.sugar.sugar.dto.SugarDTO;
import ru.gbf.sugar.sugar.dto.SugarFilter;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.mapper.SugarMapper;
import ru.gbf.sugar.sugar.repository.SugarRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SugarService {
    private final SugarRepository sugarRepository;

    public Sugar create(Sugar sugar, String filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        byte[] bytes = fileInputStream.readAllBytes();
        sugar.setImg1(bytes);
        sugar.setSync(false);

        return save(sugar);
    }

    public Sugar save(Sugar sugar) {
        return sugarRepository.save(sugar);
    }

    public List<Sugar> getAll() {
        return (List<Sugar>) sugarRepository.findAll();
    }

    public List<Sugar> getAllWithFilter(SugarFilter sugarFilter) {
        PageRequest request = PageRequest.of(
                sugarFilter.getPage(),
                sugarFilter.getSize(),
                Sort.by(Sort.Order.asc("name"))
        );
        if (sugarFilter.getName() != null) {
            if (sugarFilter.getColor() != null) {
                return sugarRepository.findAllByNameAndColor(
                        sugarFilter.getName(),
                        sugarFilter.getColor(),
                        request
                );
            }
            return sugarRepository.findAllByName(
                    sugarFilter.getName(),
                    request
            );
        }
        if (sugarFilter.getColor() != null) {
            return sugarRepository.findAllByColor(
                    sugarFilter.getColor(),
                    request
            );
        }
        return null;
    }
}
