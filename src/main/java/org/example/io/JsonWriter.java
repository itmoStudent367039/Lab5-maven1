package org.example.io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.collection.ProductCollection;
import org.example.collection.TypeCollection;
import org.example.products.Product;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JsonWriter<T> {
    private final ObjectWriter writer;
    private final ObjectMapper mapper;
    /**
     * Тут только настройка сериализатора
     */
    public JsonWriter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        writer = mapper.writer(new DefaultPrettyPrinter());
    }
    /**
     * Если файл с исходными данными - user-write, то предлагаем ввести путь до файла
     */
    public void writeToFileCollection(File file, List<T> collection) {
        File value = file;
        if (!checkFileToWrite(file)) {
            System.out.println("file without -w, please enter path to file");
            value = getFileFromUserInput();
        }
        try {
            writer.writeValue(value, collection);
            System.out.println("save successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkFileToWrite(File file) {
        return file.exists() && file.isFile() && file.canWrite();
    }

    private File getFileFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            File file1 = new File(scanner.nextLine());
            if (checkFileToWrite(file1)) {
                return file1;
            } else {
                System.out.println("Uncorrect input");
            }
        }
    }
}
