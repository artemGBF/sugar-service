package ru.gbf.sugar.sugar.servise;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ru.gbf.sugar.sugar.dto.AddFile;
import ru.gbf.sugar.sugar.dto.Token;
import ru.gbf.sugar.sugar.dto.UrlDto;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SugarServise {

    private Token token;

    public String getAuth(String code) throws IOException {
        String client_id = "7bc765b99fe348deb8ca78b2bdf9030b";
        String client_secret = "8e4b80631eda41ac9165ad8ba11b4afc";
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
        return result;
    }

    public String getAllNameFile() throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase/&fields=_embedded.items.name";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        return result;
    }

    public String addFile(HttpServletRequest request, String fileName) throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=sugarBase1/"+fileName;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
 //       objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        AddFile addFile =objectMapper.readValue(s, AddFile.class);
        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        CloseableHttpClient client1 = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(addFile.getHref());
        httpPut.setEntity(new ByteArrayEntity(bytes));
        httpGet.setHeader("Content-type", "application/json");
//        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response1 = client.execute(httpPut);
        System.out.println();
        return null;
    }

    public void getFile() throws IOException {
        String url1 = "/sugarBase/1-1.jpg";
        String url = "https://cloud-api.yandex.net/v1/disk/resources/download?path=" + URLEncoder.encode(url1, StandardCharsets.UTF_8);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        UrlDto urlDto = new ObjectMapper().readValue(result, UrlDto.class);
        CloseableHttpClient client1 = HttpClients.createDefault();
        HttpGet httpGet1 = new HttpGet(urlDto.getHref());
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response1 = client.execute(httpGet);
        HttpEntity entity = response1.getEntity();
    }

    public void createFolder(String name) throws IOException {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path=sugarBase1";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);

        System.out.println();
    }
}
