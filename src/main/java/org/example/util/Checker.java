package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class Checker {
    private static final Logger logger = Logger.getLogger(Checker.class.getName());

    public static boolean checkFileValidity(File file) {
        if ((!(file.exists() && file.isFile())) && !(file.getName().equals(""))) {
            try {
                throw new FileNotFoundException("Файл с JSON по данному пути не найден");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return file.isFile() && file.exists();
    }

    public static String checkEnvValidity(String env) {
        try {
            return Optional.ofNullable(env).orElseThrow(() -> new FileNotFoundException("Path doesn't exist"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + ": Это можно не писать в консоль");
            return "";
        }
    }
    public static boolean checkParseData(String data) {
        return true;
    }
    public static boolean checkFileToParse(File file) {
        return file.canRead();
    }
}
