package ru.gbf.sugar.sugar.servise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gbf.sugar.sugar.dto.AddGetFile;
import ru.gbf.sugar.sugar.entity.Sugar;
import ru.gbf.sugar.sugar.repository.SugarRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@Service
public class SugarServiceDb implements Serializable {

    public final String fileStorage = "/home/jj/fileStorage";
    @Autowired
    private SugarRepository sugarRepository;

    public void sugarSynchronization() throws JSONException, IOException, ParseException {
        ArrayList<String> nameFromDb = new ArrayList<>();
        Iterable<Sugar> sugarFromsDb = sugarRepository.findAll();
        for (Sugar sugar : sugarFromsDb) {
            String stringName = sugar.getColor() + "_" + sugar.getForm() + "_" + sugar.getName() + ".jpg";
            nameFromDb.add(stringName);
        }
        ArrayList<String> listFileNameFromDir = fileStorage();
        Collections.sort(nameFromDb);
        Collections.sort(listFileNameFromDir);
        if (listFileNameFromDir.equals(nameFromDb)) {
            File dir = new File(fileStorage);
            for (File file : dir.listFiles()) {
                MultipartFile multipartFile = conversionFileToMultipartFile(file);
                org.springframework.http.HttpEntity httpEntity = addFileToYandexDisk(multipartFile);
                String requestString = Objects.requireNonNull(httpEntity.getBody()).toString();
                if (requestString.equals("ok")) {
                    synchronizationsMakeTrue(file.getName());
                } else if (requestString.equals("DiskResourceAlreadyExists")) {
                    System.out.println("DiskResourceAlreadyExists");
                } else {
                    System.out.println("Проверить запись на яндекс диск"+file.getName());
                }
            }
        } else {
            System.out.println("Что то не так");
        }
    }

    private void synchronizationsMakeTrue(String fileName) {
        String substring = fileName.substring(0, fileName.length() - 4);
        String[] split = substring.split("_");
        Sugar sugarBy = sugarRepository.findSugarByColorAndFormAndName(split[0], split[1], split[2]);
        Sugar sugar = new Sugar(sugarBy.getId(), sugarBy.getColor(), sugarBy.getForm(), sugarBy.getName(), true);
        sugarRepository.save(sugar);
    }

    private MultipartFile conversionFileToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        return new MockMultipartFile(file.getName(), file.getName(), "text/plain", input.readAllBytes());
    }

    public org.springframework.http.HttpEntity addFileToYandexDisk(MultipartFile multipartFile) throws IOException, ParseException {
        String stringName = multipartFile.getName();
        String url = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=/sugarBase1/" + stringName;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-type", "application/json");
        httpGet.setHeader("Authorization", "OAuth AgAAAABQSs54AAbbEDrOVKJqbkL2vV5Ji4xJRCA");
        CloseableHttpResponse response = client.execute(httpGet);
        String s = EntityUtils.toString(response.getEntity());
        byte[] bytes = multipartFile.getBytes();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(s);
        if (json.get("error") != null) {
            org.springframework.http.HttpEntity<String> errorEntity = new org.springframework.http.HttpEntity<String>("DiskResourceAlreadyExistsError");
            return errorEntity;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        AddGetFile addFile = objectMapper.readValue(s, AddGetFile.class);
        HttpPut httpPut = new HttpPut(addFile.getHref());
        httpPut.setEntity(new ByteArrayEntity(bytes));
        httpPut.setHeader("Content-type", "application/json");
        CloseableHttpResponse response1 = client.execute(httpPut);
        int statusCode = response1.getStatusLine().getStatusCode();
        if (statusCode == 201 || statusCode == 200) {
            org.springframework.http.HttpEntity<String> ok = new org.springframework.http.HttpEntity<>("ok");
            return ok;
        } else {
            org.springframework.http.HttpEntity<String> error = new org.springframework.http.HttpEntity<>("Error");
            return error;
        }
    }

    private ArrayList<String> fileStorage() {
        File dirDb = new File(fileStorage);
        File[] files = dirDb.listFiles();
        ArrayList<String> sugarFileName = new ArrayList<String>();
        for (File file : files) {
            sugarFileName.add(file.getName());
        }
        return sugarFileName;
    }

    public void addFileToDb(String form, String color, String name, MultipartFile multipartFile) throws IOException {
        String stringName = color + "_" + form + "_" + name + ".jpg";
        Sugar sugar = new Sugar(null, name, color, form, false);
        Sugar save = sugarRepository.save(sugar);
        addFileToDir(stringName, multipartFile);
    }

    //Добавление Файла в директорию.
    public void addFileToDir(String stringName, MultipartFile multipartFile) throws IOException {
        File dir = new File(fileStorage);
        if (dir.exists()) {
            multipartFile.transferTo(new File(dir, stringName));
        } else {
            dir.mkdir();
            multipartFile.transferTo(new File(dir, stringName));
        }
    }

    public ArrayList<String> getAllNameFile() throws IOException, org.json.simple.parser.ParseException, JSONException {
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
        ArrayList<String> list = new ArrayList<>();
        int x = 0;
        while (x < items.size()) {
            JSONObject jsonObject = (JSONObject) items.get(x);
            String json = (String) jsonObject.get("name");
            list.add(json);
            x++;
        }
        return list;
    }
}
