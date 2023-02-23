package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.director.ProductDirector;
import org.example.products.Product;

import java.util.Collection;
import java.util.UUID;

public class UpdateById<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "update id {element}: обновить значения элемента из коллекции по его id";
    private ProductDirector director;

    public UpdateById(String name, ProductCollection<T> collection, ProductDirector director) {
        super(collection, name);
        this.director = director;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        try {
            UUID id = UUID.fromString(arg);
            if (super.getCollection().checkElementById(id)) {
                director.getBuilder().update(super.getCollection().getElementById(id));
                director.build();
                Product product = director.getBuilder().getProduct();
                super.getCollection().removeById(id);
                super.getCollection().add(product);
                System.out.println("Element was update successfully");
            } else {
                System.out.println("Element with this id wasn't found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
