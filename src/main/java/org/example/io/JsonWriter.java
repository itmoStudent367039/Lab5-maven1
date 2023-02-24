package org.example.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class JsonWriter<T> {
    private File file;
    private final Class<List<T>> type;
    private final ObjectMapper objectMapper;
    private ProductCollection<Collection<Product>> collection;

    public JsonWriter(File file, Class<List<T>> type, ProductCollection<Collection<Product>> collection)  {
        this.file = file;
        this.type = type;
        this.collection = collection;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void writeToFile() {
        if (!checkFileToWrite(file)) {

        }
    }

    private boolean checkFileToWrite(File file) {
        return file.exists() && file.isFile() && file.canWrite();
    }

    private File getFileFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        File file1 = new File("");
        while (checkFileToWrite(file1)) {
            file1 = new File(scanner.nextLine());
            if (checkFileToWrite(file1)) {
                return file1;
            }
        }
        return file1;
    }
}
