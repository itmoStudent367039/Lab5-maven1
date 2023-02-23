package org.example.commands;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class ClearCommand <T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "clear: очистить коллекцию";

    public ClearCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
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
        super.getCollection().clear();
    }
}
