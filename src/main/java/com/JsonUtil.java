package com;

import com.Data.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private List<Product> products;
    private String jsonString;

    //reads json file using gson and converts it into a list of java objects
    public void readJsonFile(){
        Gson gson = new Gson();
        try {
            jsonString = new String(getClass().getResourceAsStream("products.json").readAllBytes());
        } catch (IOException e) {
            System.out.println("products.json not found");
            throw new RuntimeException(e);
        }
        Type listType = new TypeToken<List<Product>>() {}.getType();
        products = gson.fromJson(jsonString, listType);
    }

    //retrieves product with specific id as java object
    public Product getProductById(int id){
        for(Product  curr: products){
            if(curr.getId()==id){
                return curr;
            }
        }
        return null;
    }

    //retrieves product with specific id as String in json format
    public String getProductByIdJson(int id){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Product product = getProductById(id);
        String json;
        //catch edge case
        if(product==null){
            json="product not found";
        }else{
            json = gson.toJson(product);
        }
        return json;
    }

    //retrieves list of products with specific category as java object
    public List<Product> getProductsByCategory(String category){
        List<Product> productsByCat = new ArrayList<Product>();
        for(Product  curr: products){
            if(curr.getCategory().equals(category)){
                productsByCat.add(curr);
            }
        }
        return productsByCat;
    }

    //retrieves list of products with specific category as String in json format
    public String getProductByCategoryJson(String category){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Product> productsByCat = getProductsByCategory(category);
        String json;
        //catch edge case
        if(productsByCat.isEmpty()){
            json = "category not found";
        }else{
            json = gson.toJson(productsByCat);
        }
        return json;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
