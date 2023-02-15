package org.example.commands;

import org.example.collection.MyCollection;
import org.example.objects.Product;

public class AddCommand implements Command {
    private String name;
    MyCollection collection;

    public AddCommand(String name, MyCollection collection) {
        this.name = name;
        this.collection = collection;
    }

    @Override
    public String getName() {
        return name;
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
