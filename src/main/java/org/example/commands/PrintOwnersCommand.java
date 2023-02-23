package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class PrintOwnersCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "print_owners: вывести значения поля owner для всех элементов колекции в порядке возрастания";

    public PrintOwnersCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        super.getCollection().printOwners();
    }
}
