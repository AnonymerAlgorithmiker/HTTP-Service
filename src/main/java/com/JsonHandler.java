package com;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class JsonHandler implements HttpHandler {
    JsonUtil jsonUtil = new JsonUtil();

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        jsonUtil.readJsonFile();
        URI uri = exchange.getRequestURI();
        String uriString = uri.toString();
        if(uriString.equals("/")){
            String response = jsonUtil.getJsonString();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
        if(uriString.startsWith("/:")){
            String response;
            try{
                int id= Integer.parseInt(uriString.substring(2));
                response = jsonUtil.getProductByIdJson(id);
            }catch(Exception e){
                response = "invalid id";
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
        if(uriString.startsWith("/?category=:")) {
            String category = uriString.substring(12);
            String response;
            if(category.isEmpty()){
                response="you need to enter a category";
            }else{
                response = jsonUtil.getProductByCategoryJson(category);
            }
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
            os.close();
        }
    }
}
