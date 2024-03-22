package commands;


import exceptions.ExecuteException;

import java.util.*;

public class CommandEditor {
    private final Map<String, Command> commandMap = new HashMap<>();

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public void execute(String[] commandArgs) throws ExecuteException {
        Command command = commandMap.get(commandArgs[0]);
        if (Objects.isNull(command)) {
            System.err.print("Такой комманды нет \n");
        } else {
            if (commandArgs.length == 1) {
                command.execute();
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(commandArgs));
                list.remove(0);
                command.execute(list.toArray(new String[0]));
            }
        }
    }
}
