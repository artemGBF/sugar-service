package ru.gbf.sugar.sugar.controller;

import org.apache.tomcat.util.json.ParseException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
    public void add(HttpServletRequest request, @RequestParam("fileName") String fileName) throws IOException {
        sugarServise.addFile(request,fileName);
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
