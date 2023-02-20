package org.example.commands;

import org.example.collection.ProductQueue;
import org.example.objects.Product;

public class AddCommand extends Command {
    private String name;
    private ProductQueue collection;
    private String description;

    public AddCommand(String name, ProductQueue collection) {
        this.name = name;
        this.collection = collection;
    }



    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(Object... objects) {
        if (objects == null || objects.length == 0) {
            System.out.println("Некорректный ввод");
        } else {
            collection.addElement((Product) objects[0]);
        }
    }
}
