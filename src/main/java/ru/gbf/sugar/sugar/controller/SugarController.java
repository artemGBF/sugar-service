package ru.gbf.sugar.sugar.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gbf.sugar.sugar.dto.CreateSugar;
import ru.gbf.sugar.sugar.dto.SugarDTO;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.service.SugarService;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/sugar")
public class SugarController {
    private final SugarService sugarService;

    @GetMapping
    public List<Sugar> getAll(){
        return sugarService.getAll();
    }

    @PostMapping("/create")
    public Sugar create(@RequestBody CreateSugar sugar) throws IOException {
        return sugarService.create(
                Sugar.builder().color(sugar.getColor()).name(sugar.getName()).build(),
                sugar.getFilename()
        );
    }

    @PostMapping("/update")
    public Sugar update(@RequestBody Sugar sugar) {
        return sugarService.save(sugar);
    }




}
