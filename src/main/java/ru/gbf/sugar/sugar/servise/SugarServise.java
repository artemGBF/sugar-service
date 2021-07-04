package ru.gbf.sugar.sugar.servise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gbf.sugar.sugar.dto.AddGetFile;
import ru.gbf.sugar.sugar.dto.FileNameDto;
import ru.gbf.sugar.sugar.dto.Token;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
public class SugarServise implements Serializable {

    private final String client_id = "7bc765b99fe348deb8ca78b2bdf9030b";
    private final String client_secret = "8e4b80631eda41ac9165ad8ba11b4afc";
    private String authToken;
    private String refreshToken;

    public String getAuth(@NotNull String code) throws IOException {
        String url = "https://oauth.yandex.ru/token";
        System.out.println(url);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Accept", "application/json");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("grant_type","authorization_code"));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("client_id", client_id));
        params.add(new BasicNameValuePair("client_secret", client_secret));
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        Token token = new ObjectMapper().readValue(result, Token.class);
        authToken=token.getAccess_token();
        return result;
    }

    public String getAllNameFile() throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase1/&fields=_embedded.items.name";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        return EntityUtils.toString(response.getEntity()) ;
    }

    public void addFile(HttpServletRequest request, MultipartFile file) throws IOException, ServletException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=/sugarBase1/"+file.getOriginalFilename();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        AddGetFile addFile = objectMapper.readValue(s, AddGetFile.class);
        ServletInputStream inputStream = request.getInputStream();
/*        byte[] bytes = inputStream.readAllBytes();*/
        byte[] bytes = file.getBytes();
        CloseableHttpClient client1 = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(addFile.getHref());
        httpPut.setEntity(new ByteArrayEntity(bytes));
        httpPut.setHeader("Content-type", "application/json");
        CloseableHttpResponse response1 = client.execute(httpPut);
    }

    public void getFile(@NotNull String fileName) throws IOException {
        String url1 = "/sugarBase1/"+fileName;
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
        boolean jpg = ImageIO.write(imag, "jpg", new File("/home/gg/Изображения", fileName));
    }

    public HttpEntity createFolder(String folgerName) throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path="+URLEncoder.encode(folgerName, StandardCharsets.UTF_8);
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
        ArrayList<String> list=JsonPath.parse(s).read("$._embedded.items[*].name");
        if(list.contains(name)){
            getFile(name);
        }
    }
}
