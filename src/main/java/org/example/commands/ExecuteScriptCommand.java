package org.example.commands;

import org.example.exceptions.ValidException;
import org.example.products.Product;
import org.example.util.Checker;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * В кратце: тут организовано чтение из консоли, рекурсию победил счетчиком, который позволяет считать только 50 команд, даже если в скрипте будет (exexutescript anyFile...),
 * т.к. ExScrCommand - объект в ед. экзэмляре, но хочу сделать подругому, это временный вариант(
 * При чтении делю строку сразу по пробелам и засовываю String[] args в метод execute() -> CommandEditor
 */
public class ExecuteScriptCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "execute_script file_name: считать и исполнить скрипт из указанного файла";
    private CommandEditor editor;
    private int counter = 0;

    public ExecuteScriptCommand(String name, CommandEditor editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * ex(String ...args) - тут путь к файлу где скрипт
     */
    @Override
    public void execute(String... arg) {
        List<String[]> list = readFileLinesOrReturnNull(getCurrentFileOrReturnNull(arg[0]));
        try {
            Optional.ofNullable(list).orElseThrow(() -> new ValidException("Не удалось прочитать файл"));
            for (String[] cmdArgs : list) {
                editor.execute(cmdArgs);
                if (counter == 50) {break;}
                counter++;
            }
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
    }

    private File getCurrentFileOrReturnNull(String arg) {
        File file = new File(arg);
        return Checker.checkFileValidity(file) ? file : null;
    }
    /**
     * Если в файле скрипта снова команда исполнить скрипт то делю только по первому пробелу, иначе (exScr /Smth smth/file) - такое не будет парситься
     */
    private List<String[]> readFileLinesOrReturnNull(File file) {
        if (Objects.isNull(file)) {
            return null;
        } else {
            List<String[]> list;
            try (Scanner scanner = new Scanner(file)) {
                list = new ArrayList<String[]>();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty()) {
                        String[] cmdArgs = line.split(" ");
                        if (cmdArgs[0].equals("execute_script")) {
                            String[] cmdArg = line.split(" ", 2);
                            list.add(cmdArg);
                        } else {
                            list.add(cmdArgs);
                        }
                    }
                }
                return list;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }
}
