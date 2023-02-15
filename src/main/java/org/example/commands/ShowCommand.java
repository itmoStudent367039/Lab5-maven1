package org.example.commands;

import org.example.collection.MyCollection;

public class ShowCommand implements Command {
    private String name;
    private MyCollection collection;
    public ShowCommand(String name, Type type, MyCollection myCollection) {
        this.name = name;
        this.collection = myCollection;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void execute(Object... objects) {

    }
}
