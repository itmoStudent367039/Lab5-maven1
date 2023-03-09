package org.example.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ExecuteException;
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
@Slf4j
public class ExecuteScriptCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "execute_script file_name: считать и исполнить скрипт из указанного файла";
    private final String name = "execute_script";
    private final Controller executeController;
    private final CommandEditor<T> editor;

    public ExecuteScriptCommand(CommandEditor<T> editor) {
        this.editor = editor;
        executeController = new Controller();
    }

    @Override
    public String getName() {
        return name;
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
        if (arg.length != 1) {
            System.out.println("Uncorrect input");
            return;
        }
        try {
            Optional<List<String[]>> list = Optional.ofNullable(readFileLinesOrReturnNull(getCurrentFileOrReturnNull(arg[0])));
            if (list.isEmpty() || list.get().size() != 1) {
                return;
            }
            for (String[] cmdArgs : list.get()) {
                editor.execute(cmdArgs);
            }
            executeController.getExcHistory().clear();
            executeController.setControlSize(0);
        } catch (ExecuteException | NoSuchElementException e) {
            log.error(e.getMessage(), e);
        }
    }

    private File getCurrentFileOrReturnNull(String arg) throws ExecuteException {
        File file = new File(arg);
        if (Checker.checkFileValidityForRead(file)) {
            executeController.addExc(arg);
            return file;
        }
        return null;
    }

    /**
     * Если в файле скрипта снова команда исполнить скрипт то делю только по первому пробелу, иначе (exScr /Smth smth/file) - такое не будет парситься
     */
    private List<String[]> readFileLinesOrReturnNull(File file) throws ExecuteException, NoSuchElementException {
        if (Optional.ofNullable(file).isEmpty()) {
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
                            try {
                                executeController.addExc(cmdArg[1]);
                                list.add(cmdArg);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                log.error(e.getMessage(), e);
                            }
                        } else {
                            list.add(cmdArgs);
                        }
                    }
                }
                return list;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
    }

    private class Controller {
        private final HashSet<String> excHistory;
        private int controlSize;

        private Controller() {
            excHistory = new HashSet<>();
        }

        private void addExc(String exc) throws ExecuteException {
            excHistory.add(exc);
            controlSize = controlSize + 1;
            checkRecursion();
        }

        private HashSet<String> getExcHistory() {
            return excHistory;
        }

        private void setControlSize(int size) {
            this.controlSize = size;
        }

        private void checkRecursion() throws ExecuteException {
            if (excHistory.size() != controlSize) {
                throw new ExecuteException("Рекурсия!");
            }
        }
    }
}
