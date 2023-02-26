package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;
import java.util.UUID;

public class RemoveByIdCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "remove_by_id id: удалить элемент из коллекции по его id, который равен заданному";

    public RemoveByIdCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String ... args) {
        try {
            UUID id = UUID.fromString(args[0]);
            super.getCollection().removeById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
