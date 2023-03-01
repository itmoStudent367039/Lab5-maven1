package org.example.application;

import org.example.commands.CommandEditor;

import java.util.Objects;
import java.util.Scanner;

public class Application {
    private CommandEditor commandEditor;
    private final Scanner scanner = new Scanner(System.in);
    public Application(CommandEditor editor) {
        commandEditor = editor;
    }
    /**
     * split(" ", 2) - только для консольного ввода (когда путь в файлу читаю, чтобы пробелы не учитывались при split(),
     * script чтение в самой комманде;
     */
    private String[] getUserInput() {
        String[] input = null;
        while (Objects.isNull(input)) {
            System.out.print("Введите команду:\n > ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                input = line.split(" ", 2);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        return input;
    }
    public void run() {
        while (true) {
            commandEditor.execute(getUserInput());
        }
    }
}
