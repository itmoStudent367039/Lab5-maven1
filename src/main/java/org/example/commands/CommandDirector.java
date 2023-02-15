package org.example.commands;

import java.util.*;

public class CommandDirector {
    private Map<String, Command> commandMap = new HashMap<>();

    public void show() {
        commandMap.keySet().forEach(System.out::println);
    }

    public List<Command> getAccessibleCommands() {
        List<Command> commands = new ArrayList<>();
        commandMap.entrySet()
                .stream()
                .forEach(entry -> commands.add(entry.getValue()));
        return commands;
    }

    public void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public void execute(String commandName, Object... objects) {
        Command command = commandMap.get(commandName);
        if (command != null) {
            command.execute(objects);
        } else {
            System.err.println("Такой команды нет");
        }
    }
}
