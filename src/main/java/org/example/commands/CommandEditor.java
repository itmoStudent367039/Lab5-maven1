package org.example.commands;

import org.example.products.Product;

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
                command.execute();
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
        } else if (commandArgs.length == 18) {
            Command command = commandMap.get(commandArgs[0]);
            if (!Objects.isNull(command)) {
                command.execute(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4], commandArgs[5], commandArgs[6], commandArgs[7], commandArgs[8],
                        commandArgs[9], commandArgs[10], commandArgs[11], commandArgs[12], commandArgs[13], commandArgs[14], commandArgs[15], commandArgs[16], commandArgs[17]);
                history.add(commandArgs[0]);
            } else {
                System.out.println("Такой команды нет");
            }
        } else {
            System.out.println("Uncorrect input");
        }
    }
}
