package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class DataUtil {
    private static DataUtil instance;

    private DataUtil() {
    }

    public static DataUtil getInstance() {
        if (instance == null) {
            instance = new DataUtil();
        }
        return instance;
    }

    public File getFile() {
        File file = null;
        boolean flag = false;
        while (!flag) {
            file = new File(getPath());
            if (Checker.checkFile(file)) {
                flag = true;
            } else {
                try {
                    throw new FileNotFoundException("File doesnt exist");
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return file;
    }

    private String getPath() {
        try {
            return Optional.ofNullable(System.getenv("FILEFORLAB")).orElseThrow(() -> new FileNotFoundException("Path doesn't exist"));
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            return getPathFromConsole();
        }
    }

    private String getPathFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a path to your file");
        return scanner.nextLine();
    }
}
