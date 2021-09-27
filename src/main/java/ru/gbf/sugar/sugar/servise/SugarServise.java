package ru.gbf.sugar.sugar.servise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.ParseException;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gbf.sugar.sugar.dto.AddGetFile;
import ru.gbf.sugar.sugar.dto.PaginationDto;
import ru.gbf.sugar.sugar.dto.Token;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.*;
import ru.gbf.sugar.sugar.repository.SugarRepository;

@Service
public class SugarServise implements Serializable {

    private final String client_id = "7bc765b99fe348deb8ca78b2bdf9030b";
    private final String client_secret = "8e4b80631eda41ac9165ad8ba11b4afc";
    private String authToken;
    private String refreshToken;
    private String pageSize = "10";

    @Autowired
    private SugarRepository sugarRepository;

    @Autowired
    SugarServiceDb sugarServiceDb;

    public String getAuth(@NotNull String code) throws IOException {
        String url = "https://oauth.yandex.ru/token";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Accept", "application/json");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("client_secret", client_secret));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        Token token = new ObjectMapper().readValue(result, Token.class);
        authToken = token.getAccess_token();
        return result;
    }

    public PaginationDto getAllNameFile(String currentPage) throws IOException, org.json.simple.parser.ParseException, JSONException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase1/&fields=_embedded.items.name";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream entityContent = entity.getContent();
        String s = EntityUtils.toString(response.getEntity());
        Object obj = new JSONParser().parse(s);
        JSONObject jo = (JSONObject) obj;
        JSONObject embedded = (JSONObject) jo.get("_embedded");
        JSONArray items = (JSONArray) embedded.get("items");
        PaginationDto list = paginationArray(items, currentPage);
        ResponseEntity<JSONObject> entity1 = new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
        return list;
    }

    private PaginationDto paginationArray(JSONArray jsonArray, String currentPage) throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        int x = Integer.parseInt(this.pageSize) * (Integer.parseInt(currentPage) - 1);
        int y = x + Integer.parseInt(pageSize);
        String total = String.valueOf(jsonArray.size());
        while (x < y && x <= jsonArray.size() - 1) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(x);
            String jo = (String) jsonObject.get("name");
            list.add(jo);
            x++;
        }
        PaginationDto paginationDto = new PaginationDto(Integer.parseInt(pageSize), Integer.parseInt(total), list);
        return paginationDto;
    }

    public void addFile(HttpServletRequest request, String form, String color, String name, MultipartFile file) throws IOException, ServletException {
        String stringName = color + "_" + form + "_" + name + ".jpg";
        String url = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=/sugarBase1/" + stringName;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        AddGetFile addFile = objectMapper.readValue(s, AddGetFile.class);
        byte[] bytes = file.getBytes();
        HttpPut httpPut = new HttpPut(addFile.getHref());
        httpPut.setEntity(new ByteArrayEntity(bytes));
        httpPut.setHeader("Content-type", "application/json");
        CloseableHttpResponse response1 = client.execute(httpPut);
    }

    public void getFile(@NotNull String fileName) throws IOException {
        String url1 = "/sugarBase1/" + fileName;
        String url = "https://cloud-api.yandex.net/v1/disk/resources/download?path=" + URLEncoder.encode(url1, StandardCharsets.UTF_8);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        AddGetFile addGetFile = new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), AddGetFile.class);
        CloseableHttpClient client1 = HttpClients.createDefault();
        HttpGet httpGet1 = new HttpGet(addGetFile.getHref());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response1 = client1.execute(httpGet1);
        byte[] bytes = response1.getEntity().getContent().readAllBytes();
        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytes));
        boolean jpg = ImageIO.write(imag, "jpg", new File("/home/jj/Изображения", fileName));
    }

    public HttpEntity createFolder(String folgerName) throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=" + URLEncoder.encode(folgerName, StandardCharsets.UTF_8);
        System.out.println(url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpPut);
        HttpEntity entity = response.getEntity();
        return entity;
    }

    public void searchSugar(String name) throws IOException, ParseException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase1/&fields=_embedded.items.name";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        ArrayList<String> list = JsonPath.parse(s).read("$._embedded.items[*].name");
        if (list.contains(name)) {
            getFile(name);
        }
    }

    public org.springframework.http.HttpEntity<Boolean> deleteFileByName(String name) throws IOException {
        String UrlDelete = "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase1/" + name;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(UrlDelete);
        httpDelete.setHeader("Content-type", "application/json");
        httpDelete.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpDelete);
        String s = response.getStatusLine().toString();
        if (s.equals("HTTP/1.1 204 NO CONTENT")) {
            String substring = name.substring(0, name.length() - 4);
            String[] split = substring.split("_");
            sugarRepository.deleteSugarByColorAndFormAndName(split[0],split[1],split[2]);
            Boolean stringHttpEntity = deleteFileByNameFromDir(name);
            return new org.springframework.http.HttpEntity<Boolean>(stringHttpEntity);
        }
        return null;
    }

    private Boolean deleteFileByNameFromDir(String name) {
        File file=new File(sugarServiceDb.fileStorage,name);
        if (file.exists()){
            boolean delete = file.delete();
            return true;
        }else {
            System.out.println("Ошибка удаления файла");
        }
        return null;
    }

    public void setPageSize(int num) {
        this.pageSize = String.valueOf(num);
    }

    public HttpEntity getListDir() throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/applications/";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(response.getEntity());
        return null;
    }
}
