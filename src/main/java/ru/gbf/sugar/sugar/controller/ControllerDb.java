package ru.gbf.sugar.sugar.controller;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gbf.sugar.sugar.servise.SugarServiceDb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "sugarDb")
public class ControllerDb implements Serializable {
    @Autowired
    private SugarServiceDb sugarServiceDb;

    @GetMapping("/synchronization")
    public  void synchronization() throws IOException, ParseException, JSONException {
        sugarServiceDb.sugarSynchronization();
    }
    @PostMapping("/addFile")
    public void add(HttpServletRequest request,
                    @RequestPart MultipartFile file,
                    @RequestPart String form,
                    @RequestPart String color,
                    @RequestPart String name) throws IOException{
        sugarServiceDb.addFileToDb(form, color, name, file);
    }
}
