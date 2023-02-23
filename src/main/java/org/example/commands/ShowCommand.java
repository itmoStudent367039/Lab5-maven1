package org.example.commands;


import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class ShowCommand <T extends Collection<Product>> extends Command<T, Product, String> {
    public final String description = "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    public ShowCommand(String name, ProductCollection<T> myCollection) {
        super(myCollection, name);
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
        super.getCollection().show();
    }
}
