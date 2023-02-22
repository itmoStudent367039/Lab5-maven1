package org.example.commands;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class ClearCommand <T extends Collection<Product>> extends Command<T, Product> {
    public static final String description = "clear";
    public static final String syntax = "kkk";

    public ClearCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
    }

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public void execute() {
        super.getCollection().clear();
    }
}
