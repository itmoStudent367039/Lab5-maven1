package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class Checker {
    private static final Logger logger = Logger.getLogger(Checker.class.getName());

    public static boolean checkFileValidityForRead(File file) {
        if (!(file.exists() && file.isFile())) {
            try {
                throw new FileNotFoundException("Файл по данному пути не найден");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        if (!file.canRead()) {
            try {
                throw new IOException("-r for file");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        if (!file.getPath().matches("\\.(txt|json)$")) {
            try {
                throw new FileNotFoundException("Uncorrect format of file");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
    }

    public static boolean checkFileValidityForWrite(File file) {
        if (!(file.exists() && file.isFile())) {
            try {
                throw new FileNotFoundException("Файл по данному пути не найден");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        if (!file.canWrite()) {
            try {
                throw new IOException("-w for file");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        if (!file.getPath().matches("\\.(txt|json)$")) {
            try {
                throw new FileNotFoundException("Uncorrect format of file");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return true;
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
        if (!Objects.isNull(env)) {
            return env;
        } else {
            try {
                throw new FileNotFoundException("Path doesn't exist");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return "";
            }
        }
    }
}
