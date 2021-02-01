package ru.gbf.sugar.sugar.controller;

import org.springframework.web.bind.annotation.*;
import ru.gbf.sugar.sugar.entity.Sugar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "sugar")
public class Controller {

    @GetMapping("/getall")
    public List<Sugar> getAll() {
        return null;
    }

    @GetMapping("/getbyid/{id}")
    public Sugar getbyid(Long id) {
        return null;
    }

    @GetMapping("/getbyparam/{param}")
    public List<Sugar> getbyparam(@PathVariable Object param) {
        return null;
    }

    @PostMapping("/add")
    public Sugar add(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @DeleteMapping("/deletebyparam/{param}")
    public Sugar deletebyparam(@PathVariable Object param) {
        return null;
    }

}
