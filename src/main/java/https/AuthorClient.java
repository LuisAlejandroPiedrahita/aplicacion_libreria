/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package https;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Api_Error;
import models.Author;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *
 * @author luis
 */
public class AuthorClient {
    
    public static final String HOST = "https://libreria-online.herokuapp.com";
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    public AuthorClient() {
        objectMapper = new ObjectMapper();
        client = new OkHttpClient().newBuilder()
                .build();
    }
     
    public void createAuthor(Author author) throws Exception{

        String bodyStr = objectMapper.writeValueAsString(author);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request request = new Request.Builder()
                .url(HOST +"/authors")
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
}
