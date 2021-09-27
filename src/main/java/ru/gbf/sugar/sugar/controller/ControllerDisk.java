package ru.gbf.sugar.sugar.controller;

import org.apache.http.HttpResponse;
import org.apache.tomcat.util.json.ParseException;
import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbf.sugar.sugar.dto.PaginationDto;
import ru.gbf.sugar.sugar.servise.SugarServise;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "sugar")
public class ControllerDisk implements Serializable {

    @Autowired
    private SugarServise sugarServise;

    @GetMapping("/createFolder")
    public HttpEntity createFolder(@RequestParam(value = "folderName") String folderName) throws IOException {
        HttpEntity httpEntity = sugarServise.createFolder(folderName);
        return httpEntity;
    }


    @GetMapping("/getAllNameFile")
    public PaginationDto getAll(@RequestParam(value = "currentPage") String currentPage) throws IOException, org.json.simple.parser.ParseException, JSONException {
        PaginationDto ob = sugarServise.getAllNameFile(currentPage);
        return ob;
    }

    @GetMapping("/getAuth")
    public String getAuth(@RequestParam String code) throws IOException {
        return sugarServise.getAuth(code);
    }

    @GetMapping("/getFile")
    public void getFile(@RequestParam(value = "name") String name) throws IOException {
        sugarServise.getFile(name);
    }

    @DeleteMapping("/deleteByName/{param}")
    public org.springframework.http.HttpEntity<Boolean> deleteByName(@PathVariable String param) throws IOException {
        return sugarServise.deleteFileByName(param);
    }

    @GetMapping("/search")
    public void searchSugar(@RequestParam("name") String name) throws IOException, ParseException {
        sugarServise.searchSugar(name);
    }

    @GetMapping("/setPageSize")
    public void setPageSize(@RequestParam("num") int num) throws IOException {
        sugarServise.setPageSize(num);
    }

    @GetMapping("/createDir")
    public void createDir(@RequestParam("nameDir") String nameDir) throws IOException {
        sugarServise.createFolder(nameDir);
    }

    @GetMapping("/getListDir")
    public HttpEntity createDir() throws IOException {
        HttpEntity listDir = sugarServise.getListDir();
        return listDir;
    }
}