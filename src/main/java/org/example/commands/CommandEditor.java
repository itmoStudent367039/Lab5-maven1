package org.example.commands;

import java.util.*;

public class CommandEditor {
    private Map<String, Command> commandMap = new HashMap<>();
    private List<String> history = new ArrayList<>();
    public List<String> getHistory() {
        return history;
    }
    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
    public void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public void execute(String[] commandArgs) {
        if (commandArgs.length == 1) {
            Command command = commandMap.get(commandArgs[0]);
            if (!Objects.isNull(command)) {
                command.execute(null);
                history.add(commandArgs[0]);
            } else {
                System.err.println("Такой команды нет");
            }
        } else if (commandArgs.length == 2) {
            Command command = commandMap.get(commandArgs[0]);
            if (!Objects.isNull(command)) {
                command.execute(commandArgs[1]);
                history.add(commandArgs[0]);
            } else {
                System.err.println("Такой команды нет");
            }
        } else {
            System.out.println("Uncorrect input");
        }
    }
}
