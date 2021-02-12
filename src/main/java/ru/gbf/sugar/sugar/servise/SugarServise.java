package ru.gbf.sugar.sugar.servise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ru.gbf.sugar.sugar.dto.Token;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SugarServise {

    public String getAuth(String code) throws IOException {
        String client_id="7bc765b99fe348deb8ca78b2bdf9030b";
        String client_secret="8e4b80631eda41ac9165ad8ba11b4afc";
        String url="https://oauth.yandex.ru/token";
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
        Token token=new ObjectMapper().readValue(result,Token.class);
        return result;
    }

    public String getAll() throws IOException {
        String url= "https://cloud-api.yandex.net/v1/disk/resources?path=/sugarBase/&fields=_embedded.items.name";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        return result;
    }
}
