/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package https;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import models.Api_Error;
import models.Publisher;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *
 * @author luis
 */
public class PublisherClient {
    
    public static final String HOST = "https://libreria-online.herokuapp.com";
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    public PublisherClient() {
        objectMapper = new ObjectMapper();
        client = new OkHttpClient().newBuilder()
                .build();
    }

    public void createPublisher(Publisher publisher) throws Exception{
        
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"code\": "+publisher.getCode()+",\n    \"name\": \""+publisher.getName()+"\"\n}");
        Request request = new Request.Builder()
                .url(HOST +"/publishers")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            String bodyAsString = response.body().string();
            Api_Error error = objectMapper.readValue(bodyAsString, Api_Error.class);
            throw new Exception(error.getMessage());
        }
    }
    
    public List<Publisher> getPublishers() throws IOException {
        
        Request request = new Request.Builder()
                .url(HOST +"/publishers")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String bodyAsString = response.body().string();
        Publisher[] publishers = objectMapper.readValue(bodyAsString, Publisher[].class);        
        List<Publisher> lista = Arrays.asList(publishers);
        return lista;
    }
}
