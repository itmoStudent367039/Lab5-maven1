package org.example.commands;

import org.example.collection.TypeCollection;
import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.Collection;

public class AddIfMaxCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add_if_max: добавить новый элемент в коллекцию, если его значение наибольшего элемента в этой коллекции";

    public AddIfMaxCommand(String name, TypeCollection<?, Product> collection) {
        super(collection, name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String... args) {
        try {
            Product product = AddCommand.buildProduct(args);
            if (super.getCollection().size() == 0) {
                super.getCollection().add(product);
                System.out.println("Product was add");
                return;
            }
            if (product.compareTo(super.getCollection().head()) < 0) {
                super.getCollection().add(product);
                System.out.println("Product was add");
            } else {
                System.out.println("Your product less than max of collection or equals it");
            }
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
    }
}
