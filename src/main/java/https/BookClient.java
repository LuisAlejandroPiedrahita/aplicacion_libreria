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
import models.Book;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 *
 * @author luis
 */
public class BookClient {
    
    public static final String HOST = "https://libreria-online.herokuapp.com";
    private ObjectMapper objectMapper;
    private OkHttpClient client;

    public BookClient() {
        objectMapper = new ObjectMapper();
        client = new OkHttpClient().newBuilder()
                .build();
    }
    
    public void createBook(Book book,int codePublisher) throws Exception{

        String bodyStr = objectMapper.writeValueAsString(book);
        MediaType mediaType = MediaType.parse("application/json");        
        RequestBody body = RequestBody.create(mediaType, bodyStr);
        Request request = new Request.Builder()
                .url(HOST +"/books/"+codePublisher)
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
    
    public List<Book> getBooks() throws IOException {
        Request request = new Request.Builder()
                .url(HOST +"/books")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String bodyAsString = response.body().string();
        Book[] books = objectMapper.readValue(bodyAsString, Book[].class);        
        List<Book> lista = Arrays.asList(books);
        return lista;
    }
}
