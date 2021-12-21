package ru.gbf.sugar.sugar.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.service.SugarService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/sugar")
public class SugarController {
    private final SugarService sugarService;




}
