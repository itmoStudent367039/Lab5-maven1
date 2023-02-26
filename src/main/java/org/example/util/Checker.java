package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class Checker {
    private static final Logger logger = Logger.getLogger(Checker.class.getName());

    public static boolean checkFileValidity(File file) {
        if ((!(file.exists() && file.isFile())) && !(file.getName().equals(""))) {
            try {
                throw new FileNotFoundException("Файл по данному пути не найден");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!file.canRead()) {
            try {
                throw new IOException("-r for file");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return file.canRead() && file.isFile() && file.exists();
    }

    public static boolean checkDataToParse(String line) {
        if (line.trim().isEmpty()) {
            try {
                throw new IllegalAccessException("Empty file");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return !line.trim().isEmpty();
    }

    public static String getEnvValidity(String env) {
        try {
            return Optional.ofNullable(env).orElseThrow(() -> new FileNotFoundException("Path doesn't exist"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + ": Это можно не писать в консоль");
            return "";
        }
    }
    public static boolean fileIsEmpty(File file) {
        return file.length() == 0;
    }

}
