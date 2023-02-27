package org.example.util;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Это единственный синглтон* Сделал все хардкордно: в конструкторе устанавливается файл - значение
 * переменной окружения (getFile()) - если ссылки не существует, то просит ввести из консоли
 */
public class DataUtil {
    private static DataUtil instance;
    private File file;

    /**
    * Вот конструктор - при создании объекта инициализируется файл
     */
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
    /**
     * Пробуем сначала из перем. окр., если ф. по пути некорректный, то просим ввести пользователя путь
     * Все с файлами вынесены в отдельный класс - util.Checker (методы - проверки - static)
     */
    private void setFile() {
        file = new File(setPathFromEnv());
        while (!Checker.checkFileValidity(file)) {
            file = new File(setPathFromConsole());
        }
    }

    private String setPathFromEnv() {
        return Checker.getEnvValidity(System.getenv("FILEFORLAB"));
    }

    private String setPathFromConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a path to your file");
        String line = "";
        try {
        line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        scanner.close();
        return line;
    }
}
