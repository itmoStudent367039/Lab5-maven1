package org.example.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.util.Checker;

import java.io.*;
import java.util.*;

public class JsonReader<T> {
    private final File file;
    private List<T> elementList;
    private final Class<T[]> type;
    private final ObjectMapper mapper;

    public JsonReader(File file, Class<T[]> type) {
        this.file = file;
        this.type = type;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }

    public List<T> getElementsAsList() {
        parseSetElementsListFromFile(readDataFromFile());
        return Optional.ofNullable(elementList).orElseGet(ArrayList::new);
    }

    private String readDataFromFile() {
        char[] buffer = null;
        try (FileReader reader = new FileReader(file)) {
            buffer = new char[(int) file.length()];
            reader.read(buffer);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return Objects.isNull(buffer) ? "" : String.valueOf(buffer);
    }

    private void parseSetElementsListFromFile(String data) {
        if (Checker.checkDataToParse(data)) {
            try {
                this.elementList = Arrays.asList(mapper.readValue(data, type));
            } catch (JsonProcessingException e) {
                System.err.println("Uncorrect input");
               e.printStackTrace();
            }
        }
    }
}
