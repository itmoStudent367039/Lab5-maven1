package org.example.util;

import java.io.File;
import java.io.FileNotFoundException;

public class Checker {
    public static boolean checkFile(File file) {
        return file.isFile() && file.exists();
    }
}
