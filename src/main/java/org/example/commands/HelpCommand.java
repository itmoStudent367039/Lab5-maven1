package org.example.commands;


import org.example.collection.ProductQueue;

public class HelpCommand extends Command {
    private String name;
    private ProductQueue collection;
    private CommandDirector director;

    private String description;
    public HelpCommand(String name, ProductQueue collection, CommandDirector commandDirector) {
        this.name = name;
        this.collection = collection;
        this.director = commandDirector;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(Object... objects) {

    }
}
