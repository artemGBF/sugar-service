package ru.gbf.sugar.sugar.controller;

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
public class Controller {

    @Autowired
    private SugarServise sugarServise;

    @GetMapping("/getall")
    public String getAll() throws IOException {
        String all = sugarServise.getAll();
        return all;
    }

    @GetMapping("/getauth")
    public String getAuth(@RequestParam String code) throws IOException   {
        String auth = sugarServise.getAuth(code);
        return auth;
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
