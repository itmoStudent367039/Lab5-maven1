package org.example.application;

import org.example.collection.ProductCollection;
import org.example.collection.TypeCollection;
import org.example.commands.CommandEditor;
import org.example.products.Product;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

public class Application {
    private CommandEditor commandEditor;
    private final Scanner scanner = new Scanner(System.in);
    public Application(CommandEditor editor) {
        commandEditor = editor;
    }
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
