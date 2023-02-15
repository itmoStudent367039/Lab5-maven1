package org.example.commands;

import org.example.collection.MyCollection;

public class ClearCommand implements Command {
    private String name;
    private MyCollection collection;

    public ClearCommand(String name, MyCollection collection) {
        this.name = name;
        this.collection = collection;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(Object... objects) {
        collection.clear();
    }
}
