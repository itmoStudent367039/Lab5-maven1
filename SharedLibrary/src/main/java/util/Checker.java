package util;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class Checker {

    public static boolean checkFileValidityForRead(File file) throws IOException {
        return checkFileExists(file) && checkFileToRead(file);
    }

    private static boolean checkFileToRead(File file) throws IOException {
        if (!file.canRead()) {
            throw new IOException(String.format("-r for file: %s", file.getPath()));
        }
        return true;
    }

    private static boolean checkFileExists(File file) throws FileNotFoundException {
        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException(String.format("Файл по данному пути не найден: %s", file.getPath()));
        }
        return true;
    }

    public static boolean checkFileValidityForWrite(File file) throws IOException {
        return checkFileExists(file) && checkFileToWrite(file);
    }

    private static boolean checkFileToWrite(File file) throws IOException {
        if (!file.canWrite()) {
            throw new IOException(String.format("-w for file: %s", file.getPath()));
        }
        return true;
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
