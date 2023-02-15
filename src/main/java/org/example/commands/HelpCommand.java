package org.example.commands;

import org.example.collection.MyCollection;

public class HelpCommand implements Command {
    private String name;
    private MyCollection collection;
    private CommandDirector director;

    public HelpCommand(String name, MyCollection collection, CommandDirector commandDirector) {
        this.name = name;
        this.collection = collection;
        this.director = commandDirector;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(Object... objects) {

    }
}
