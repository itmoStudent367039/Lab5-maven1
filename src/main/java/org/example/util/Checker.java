package org.example.util;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
@Slf4j
public class Checker {
    private static final String JSON_OR_TXT_FORMAT = "^.*\\.(json|txt)$";
    public static boolean checkFileValidityForRead(File file) {
        return checkFileExists(file) && checkFileToRead(file) && checkFormatOfFile(file);
    }
    private static boolean checkFileToRead(File file) {
        if (!file.canRead()) {
            try {
                throw new IOException(String.format("-r for file: %s", file.getPath()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }
    private static boolean checkFormatOfFile(File file) {
        if (!file.getPath().matches(JSON_OR_TXT_FORMAT)) {
            try {
                throw new IOException(String.format("File: %s isn't valid format", file.getPath()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    private static boolean checkFileExists(File file) {
        if (!(file.exists() && file.isFile())) {
            try {
                throw new FileNotFoundException(String.format("Файл по данному пути не найден: %s", file.getPath()));
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean checkFileValidityForWrite(File file) {
        return checkFileExists(file) && checkFileToWrite(file) && checkFormatOfFile(file);
    }

    private static boolean checkFileToWrite(File file) {
        if (!file.canWrite()) {
            try {
                throw new IOException(String.format("-w for file: %s", file.getPath()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean checkDataToParse(String line) {
        if (line.trim().isEmpty()) {
            try {
                throw new IllegalAccessException("Empty line");
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return !line.trim().isEmpty();
    }

    public static String getEnvValidity(String env) {
        if (!Objects.isNull(env)) {
            return env;
        } else {
            try {
                throw new FileNotFoundException("Path doesn't exist");
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
                System.out.println(e.getMessage());
                return "";
            }
        }
    }
}
