package org.example.commands;


import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;
import java.util.stream.Collectors;

public class HelpCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private CommandEditor editor;
    private final String description = "help: вывести справку по доступным командам";

    public HelpCommand(String name, ProductCollection<T> collection, CommandEditor commandDirector) {
        super(collection, name);
        this.editor = commandDirector;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute(String arg) {
        System.out.println(editor.getCommandMap()
                .values()
                .stream()
                .map(Command::getDescription)
                .collect(Collectors.joining(System.lineSeparator())));
    }
}
