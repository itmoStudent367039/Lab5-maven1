package org.example.commands;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class ClearCommand <T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "clear: очистить коллекцию";
    private final String name = "clear";

    public ClearCommand(ProductCollection<T> collection) {
        super(collection);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public void execute(String ... args) {
        super.getCollection().clear();
    }
}
