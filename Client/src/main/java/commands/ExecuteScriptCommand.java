package commands;

import exceptions.ExecuteException;
import exceptions.ExecuteScriptException;
import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import util.Checker;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Slf4j
public class ExecuteScriptCommand extends Command {
    private final String name = "execute_script";
    private final Client<Serializable> client;
    private final CommandEditor editor;
    private final Controller executeController;

    public ExecuteScriptCommand(Client<Serializable> client, CommandEditor editor) {
        this.client = client;
        this.editor = editor;
        this.executeController = new Controller();
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void execute(String... args) throws ExecuteException {
        try {
            File file = getCurrentFileOrThrowValidException(args);
            Optional<List<String[]>> list = Optional.ofNullable(readFileLinesOrReturnNull(file));
            for (String[] cmdArgs : list.orElseThrow(() -> new ExecuteException("Failed to read file"))) {
                Command command = editor.getCommandMap().get(cmdArgs[0]);
                if (!Objects.isNull(command)) {
                    if (cmdArgs.length == 1) {
                        command.execute();
                    } else {
                        List<String> argsList = new ArrayList<>(Arrays.asList(cmdArgs));
                        argsList.remove(0);
                        command.execute(argsList.toArray(new String[0]));
                    }
                }
            }
        } catch (ValidException | IOException e) {
            throw new ExecuteException(e);
        } catch (ExecuteScriptException e) {
            log.error(e.getMessage(), e);
        } finally {
            executeController.getExcHistory().clear();
            executeController.setZeroSize();
        }
    }

    private File getCurrentFileOrThrowValidException(String[] args) throws ValidException, IOException, ExecuteScriptException {
        if (args.length == 1) {
            File file = new File(args[0]);
            if (Checker.checkFileValidityForRead(file)) {
                executeController.addExc(file.getAbsolutePath());
                return file;
            }
        }
        throw new ValidException("Uncorrect input");
    }

    private List<String[]> readFileLinesOrReturnNull(File file) throws NoSuchElementException {
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
                        if (cmdArgs[0].equals(this.name)) {
                            String[] cmdArg = line.split(" ", 2);
                            list.add(cmdArg);
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

    private static class Controller {
        private final HashSet<String> excHistory;
        private int controlSize;

        private Controller() {
            excHistory = new HashSet<>();
        }

        private void addExc(String exc) throws ExecuteScriptException {
            excHistory.add(exc);
            controlSize = controlSize + 1;
            checkRecursion();
        }

        private HashSet<String> getExcHistory() {
            return excHistory;
        }

        private void setZeroSize() {
            this.controlSize = 0;
        }

        private void checkRecursion() throws ExecuteScriptException {
            if (excHistory.size() != controlSize) {
                throw new ExecuteScriptException("Рекурсия!");
            }
        }
    }
}
