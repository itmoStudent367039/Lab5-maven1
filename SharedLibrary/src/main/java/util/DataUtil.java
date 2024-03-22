package util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Это единственный синглтон* Сделал все хардкордно: в конструкторе устанавливается файл - значение
 * переменной окружения (getFile()) - если ссылки не существует, то просит ввести из консоли
 */
@Slf4j
public class DataUtil {
    private static DataUtil instance;
    private File file;
    private final Scanner scanner = new Scanner(System.in);

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
        String MBTilda = setPathFromEnv();
        File file = new File(MBTilda);
        try {
            Checker.checkFileValidityForRead(file);
            this.file = file;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            this.file = setFileFromConsole();
        }
    }

    private String setPathFromEnv() {
        return Checker.getEnvValidity(System.getenv("FILEFORLA"));
    }

    private File setFileFromConsole() {
        while (true) {
            System.out.print("Enter path to file \n > ");
            String path = null;
            try {
                path = scanner.nextLine();
            } catch (NoSuchElementException e) {
                log.error(e.getMessage(), e);
                System.exit(0);
            }
            File file = new File(path);
            try {
                Checker.checkFileValidityForRead(file);
                return file;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
