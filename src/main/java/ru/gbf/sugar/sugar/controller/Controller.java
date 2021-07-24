package ru.gbf.sugar.sugar.controller;

import org.apache.http.HttpResponse;
import org.apache.tomcat.util.json.ParseException;
import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gbf.sugar.sugar.dto.PaginationDto;
import ru.gbf.sugar.sugar.servise.SugarServise;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public PaginationDto getAll(@RequestParam(value = "currentPage") String currentPage) throws IOException, org.json.simple.parser.ParseException, JSONException {
        PaginationDto ob= sugarServise.getAllNameFile(currentPage);
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

    @PostMapping("/addFile")
    public void add(HttpServletRequest request,
                    @RequestPart MultipartFile file,
                    @RequestPart String form,
                    @RequestPart String date,
                    @RequestPart String color,
                    @RequestPart String place,
                    @RequestPart String name) throws IOException, ServletException {
        sugarServise.addFile(request,form,date,color,place,name, file);
    }

    @DeleteMapping ("/deleteByName/{param}")
    public HttpResponse deleteByName(@PathVariable String param) throws IOException {
      return   sugarServise.deleteFileByName(param);
    }

    @GetMapping("/search")
    public void searchSugar(@RequestParam("name") String name) throws IOException, ParseException {
        sugarServise.searchSugar(name);
        System.out.println();
    }
    @GetMapping("/setPageSize")
    public void setPageSize(@RequestParam("num") int num) throws IOException {
        System.out.println(num);
        sugarServise.setPageSize(num);
    }
}
