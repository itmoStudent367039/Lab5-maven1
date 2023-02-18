package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;

public class Checker {
    public static boolean checkFileValidity(File file) throws FileNotFoundException {
        if (!(file.exists() && file.isFile())) {
            throw new FileNotFoundException("Файл с JSON по данному пути не найден");
        }
        return file.isFile() && file.exists();
    }
}
