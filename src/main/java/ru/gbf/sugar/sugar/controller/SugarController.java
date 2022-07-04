package ru.gbf.sugar.sugar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gbf.sugar.sugar.dto.SugarDTO;
import ru.gbf.sugar.sugar.dto.SugarFilter;
import ru.gbf.sugar.sugar.mapper.SugarMapper;
import ru.gbf.sugar.sugar.service.SugarService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/sugar")
public class SugarController {
    private final SugarService sugarService;
    private final SugarMapper sugarMapper;

    @GetMapping
    public List<SugarDTO> getAll() {
        return sugarService.getAll().stream()
                .map(sugarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    List<SugarDTO> getAllWithFilter(@RequestBody SugarFilter filter) {
        return sugarService.getAllWithFilter(filter).stream()
                .map(sugarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public SugarDTO save(@RequestBody SugarDTO sugar) throws IOException {
        return sugarMapper.toDTO(
                sugarService.save(sugarMapper.toEntity(sugar))
        );
    }


}
