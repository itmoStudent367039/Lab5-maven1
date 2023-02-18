package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class DataUtil {
    private static DataUtil instance;
    private File file;

    private DataUtil() {
        setFile();
    }

    public static DataUtil getInstance() {
        if (instance == null) {
            instance = new DataUtil();
        }
        return instance;
    }

    public File getFile() {
        return file;
    }

    private void setFile() {
        file = new File(setPathFromEnv());
        while (!Checker.checkFileValidity(file)) {
            file = new File(setPathFromConsole());
        }
    }

    private String setPathFromEnv() {
        return Checker.checkEnvValidity(System.getenv("FILEFORLAB"));
    }

    private String setPathFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a path to your file");
        return scanner.nextLine();
    }
}
