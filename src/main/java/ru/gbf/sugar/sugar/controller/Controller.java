package ru.gbf.sugar.sugar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.gbf.sugar.sugar.entity.Sugar;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbf.sugar.sugar.servise.SugarServise;


@RestController
@RequestMapping(value = "sugar")
public class Controller {

    @Autowired
    private SugarServise sugarServise;

    @GetMapping("/createFolder")
    public Sugar createFolder(@RequestParam String name) throws IOException {
        sugarServise.createFolder(name);
        System.out.println();
        return null;
    }

    @GetMapping("/getAllNameFile")
    public String getAll() throws IOException {
        String all = sugarServise.getAllNameFile();
        return all;
    }

    @GetMapping("/getAuth")
    public String getAuth(@RequestParam String code) throws IOException   {
        String auth = sugarServise.getAuth(code);
        return auth;
    }
    

    @GetMapping("/getbyid/{id}")
    public Sugar getbyid(Long id) {
        return null;
    }

    @GetMapping("/getFile")
    public List<Sugar> getbyparam() throws IOException {
        sugarServise.getFile();
        return null;
    }

    @PostMapping("/add")
    public Sugar add( HttpServletRequest request,@RequestParam("fileName") String fileName) throws IOException {
        sugarServise.addFile(request,fileName);
        return null;
    }

    @DeleteMapping("/deletebyparam/{param}")
    public Sugar deletebyparam(@PathVariable Object param) {
        return null;
    }

}
