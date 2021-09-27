package ru.gbf.sugar.sugar.init;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class InitAuth {

    @PostConstruct
    private void postConstruct() throws IOException {
        String url="https://oauth.yandex.ru/authorize?";
        String url1="response_type=code&client_id=7bc765b99fe348deb8ca78b2bdf9030b";
        CloseableHttpClient client= HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url+URLEncoder.encode(url1, StandardCharsets.UTF_8));
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse httpResponse = client.execute(httpGet);
    }

}
