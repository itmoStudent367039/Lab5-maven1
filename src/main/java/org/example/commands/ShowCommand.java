package org.example.commands;


import org.example.collection.ProductQueue;

public class ShowCommand extends Command {
    private String name;
    private ProductQueue collection;
    private String description;
    public ShowCommand(String name, ProductQueue myCollection) {
        this.name = name;
        this.collection = myCollection;
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
