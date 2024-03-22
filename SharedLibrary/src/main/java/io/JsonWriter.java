package io;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import util.Checker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Slf4j
public class JsonWriter<T> {
    private final ObjectWriter writer;
    private final ObjectMapper mapper;
    private final Scanner scanner = new Scanner(System.in);

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
    public void writeToFileCollection(File file, List<T> collection) throws IOException {
        File value = file;
        if (!Checker.checkFileValidityForWrite(file)) {
            value = getFileFromUserInput();
        }
        try {
            writer.writeValue(value, collection);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private File getFileFromUserInput() throws IOException {
        while (true) {
            String path = null;
            try {
                path = scanner.nextLine().replaceFirst("^~", System.getenv("HOME"));
            } catch (NoSuchElementException e) {
                log.warn(e.getMessage(), e);
                System.exit(0);
            }
            File file1 = new File(path);
            if (Checker.checkFileValidityForWrite(file1)) {
                return file1;
            }
        }
    }
}
