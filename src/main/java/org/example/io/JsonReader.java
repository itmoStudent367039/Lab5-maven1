package org.example.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jdk.jshell.UnresolvedReferenceException;
import org.example.util.Checker;

import java.io.*;
import java.util.*;

public class JsonReader<T> {
    private File file;
    private List<T> elementList;
    private final Class<T[]> type;
    private ObjectMapper mapper;
    private JavaTimeModule javaTimeModule;

    public JsonReader(File file, Class<T[]> type) {
        this.file = file;
        this.type = type;
        this.mapper = new ObjectMapper();
        this.javaTimeModule = new JavaTimeModule();
        this.mapper.registerModule(javaTimeModule);
        this.setElementList();
    }

    public List<T> getElementsAsList() {
        return elementList;
    }
    private void setElementList() {
        elementList = Checker.checkFileToParse(file) ? parseGetElementsListFromFile(readDataFromFile()) : new ArrayList<T>();
    }

    private String readDataFromFile() {
        char[] buffer = null;
        try (FileReader reader = new FileReader(file)) {
            buffer = new char[(int) file.length()];
            while (reader.ready()) {
                reader.read(buffer);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return Objects.isNull(buffer) ? "" : String.valueOf(buffer);
    }

    private List<T> parseGetElementsListFromFile(String data) {
        try {
            return Arrays.asList(mapper.readValue(data, type));
        } catch (MismatchedInputException e) {
            System.out.println(e.getMessage());
            return new ArrayList<T>();
        } catch (JsonMappingException e1) {
           System.out.println(e1.getMessage());
           // e1.printStackTrace();
            return null;
        } catch (JsonProcessingException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
