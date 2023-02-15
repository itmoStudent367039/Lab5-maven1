package org.example.collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.objects.Coordinates;
import org.example.objects.Person;
import org.example.objects.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonReader {
    private String path;

    public JsonReader(String path) {
        this.path = path;
    }

    public List<Product> getProducts() {
        ObjectMapper mapper = new ObjectMapper();
        List<Product> list = new ArrayList<>();
        try (FileReader reader = new FileReader(path)) {
            Product product = mapper.readValue(reader, Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
