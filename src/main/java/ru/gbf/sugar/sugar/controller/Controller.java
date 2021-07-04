package ru.gbf.sugar.sugar.controller;

import org.apache.http.HttpHeaders;
import org.apache.tomcat.util.json.ParseException;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbf.sugar.sugar.entity.Sugar;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import java.io.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbf.sugar.sugar.servise.SugarServise;


@RestController
@RequestMapping(value = "sugar")
//@CrossOrigin(origins = "*")
public class Controller implements Serializable {

    @Autowired
    private SugarServise sugarServise;

    @GetMapping("/createFolder")
    public HttpEntity createFolder(@RequestParam String folgerName) throws IOException {
        HttpEntity httpEntity = sugarServise.createFolder(folgerName);
        return null;
    }


    @GetMapping("/getAllNameFile")
    public String getAll() throws IOException {
        return sugarServise.getAllNameFile();
    }

    @GetMapping("/getAuth")
    public String getAuth(@RequestParam String code) throws IOException {
        return sugarServise.getAuth(code);
    }

    @GetMapping("/getFile")
    public void getFile(@RequestParam("fileName") String fileName) throws IOException {
        sugarServise.getFile(fileName);
    }

    @PostMapping("/addFile")
    public void add(HttpServletRequest request, @RequestParam(value="file") MultipartFile file) throws IOException, ServletException {
        sugarServise.addFile(request, file );
    }

    @DeleteMapping("/deletebyparam/{param}")
    public Sugar deletebyparam(@PathVariable Object param) {
        return null;
    }

    @GetMapping("/search")
    public void searchSugar(@RequestParam("name") String name) throws IOException, ParseException {
        sugarServise.searchSugar(name);
        System.out.println();
    }


}
