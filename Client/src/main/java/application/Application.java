package application;

import commands.CommandEditor;
import exceptions.ExecuteException;
import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class Application {
    private final CommandEditor commandEditor;
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
            String line = null;
            try {
                line = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                log.error(e.getMessage(), e);
                System.exit(0);
            }
            if (!line.isEmpty()) {
                input = line.split(" ", 2);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        return input;
    }

    public void run() {
        System.out.println("send_pending_request - {int}, show_pending_requests");
        while (true) {
            try {
                commandEditor.execute(getUserInput());
            } catch (ExecuteException e) {
                if (!Objects.isNull(e.getCause())) {
                    log.error(e.getMessage(), e.getCause());
                } else {
                    log.error(e.getMessage(), e);
                }
                System.out.println(e.getMessage());
            }
        }
    }
}
