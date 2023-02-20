package org.example.commands;
import org.example.collection.ProductQueue;

public class ClearCommand extends Command {
    private String name;
    private ProductQueue collection;
    private String description;

    public ClearCommand(String name, ProductQueue collection) {
        this.name = name;
        this.collection = collection;
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
        collection.clear();
    }
}
