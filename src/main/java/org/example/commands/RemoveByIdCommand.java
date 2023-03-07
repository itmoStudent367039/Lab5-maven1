package org.example.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;
import java.util.UUID;
@Slf4j
public class RemoveByIdCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "remove_by_id id: удалить элемент из коллекции по его id, который равен заданному";
    private final String name = "remove_by_id";

    public RemoveByIdCommand(ProductCollection<T> collection) {
        super(collection);
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
    public void execute(String ... args) {
        if (args.length != 1) {
            System.out.println("Empty input");
            return;
        }
        try {
            UUID id = UUID.fromString(args[0]);
            if (super.getCollection().removeById(id)) {
                System.out.println("delete successfully");
                log.info(String.format("%s was deleted successfully", id));
            } else {
                System.out.println("element with this id wasn't found");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }
}
