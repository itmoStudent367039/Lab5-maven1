package org.example.commands;

import org.example.products.Product;

import java.util.*;

public class CommandEditor<T extends Collection<Product>> {
    private final Map<String, Command<T, Product>> commandMap = new HashMap<>();
    private final List<String> history = new ArrayList<>();

    public List<String> getHistory() {
        return history;
    }

    public Map<String, Command<T, Product>> getCommandMap() {
        return commandMap;
    }

    public void addCommand(Command<T, Product> command) {
        commandMap.put(command.getName(), command);
    }

    public void execute(String[] commandArgs) {
        Command<T, Product> command = commandMap.get(commandArgs[0]);
        if (Objects.isNull(command)) {
            System.err.println("Такой комманды нет");
        } else {
            if (commandArgs.length == 1) {
                command.execute();
            } else {
                List<String> list = new ArrayList<>(Arrays.asList(commandArgs));
                list.remove(0);
                command.execute(list.toArray(new String[0]));
            }
            history.add(commandArgs[0]);
        }
    }
}
