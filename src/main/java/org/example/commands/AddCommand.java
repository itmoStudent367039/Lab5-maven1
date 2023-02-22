package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.director.ProductDirector;
import org.example.products.Product;

import java.util.Collection;

public class AddCommand<T extends Collection<Product>> extends Command<T, Product> {
    public final static String description = "добавить новый элемент в коллекцию";
    public final static String syntax = "add {element}";
    private ProductDirector productDirector;

    public AddCommand(String name, ProductCollection<T> collection, ProductDirector director) {
        super(collection, name);
        this.productDirector = director;
    }

    @Override
    public void execute() {
        productDirector.build();
        Product product = productDirector.getBuilder().getProduct();
        super.getCollection().add(product);
    }
}
