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
        File file = new File(setPathFromEnv());
        try {
            Checker.checkFileValidity(file);
        } catch (FileNotFoundException e) {
            boolean flag = false;
            while (!flag) {
                file = new File(setPathFromConsole());
                try {
                    flag = Checker.checkFileValidity(file);
                } catch (FileNotFoundException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        }
        this.file = file;
    }

    private String setPathFromEnv() {
        try {
            return Optional.ofNullable(System.getenv("FILEFORLA")).orElseThrow(() -> new FileNotFoundException("Path doesn't exist"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private String setPathFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a path to your file");
        return scanner.nextLine();
    }
}
