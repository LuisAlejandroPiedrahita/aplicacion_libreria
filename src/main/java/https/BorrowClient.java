/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package https;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Api_Error;
import models.Borrow;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *
 * @author luis
 */
public class BorrowClient {
    
    public static final String HOST = "https://libreria-online.herokuapp.com";
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    public BorrowClient() {
        objectMapper = new ObjectMapper();
        client = new OkHttpClient().newBuilder()
                .build();        
    }
    
    public void createBorrow(Borrow borrow) throws Exception{

        String bodyStr = objectMapper.writeValueAsString(borrow);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"id\": "+borrow.getId()+",\n  \"userId\": \""+borrow.getIdUser()+"\",\n    \"bookId\": \""+borrow.getIdBook()+"\"\n}");      
        System.out.println(body);
        Request request = new Request.Builder()
                .url(HOST +"/borrows")
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
    
    public void deleteBorrow(int idBorrow) throws Exception{      

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(HOST +"/borrows/"+idBorrow)
                .method("DELETE", body)
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
