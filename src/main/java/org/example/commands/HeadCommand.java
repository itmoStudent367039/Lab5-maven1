package org.example.commands;

import org.example.collection.TypeCollection;
import org.example.products.Product;

import java.util.Collection;

public class HeadCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "head: вывести первый элемент коллекции";

    public HeadCommand(String name, TypeCollection<?, Product> collection) {
        super(collection, name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        super.getCollection().head();
    }
}
