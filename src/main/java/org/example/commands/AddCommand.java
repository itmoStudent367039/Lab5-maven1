package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.director.ProductDirector;
import org.example.products.Product;

import java.util.Collection;

public class AddCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "add {element}: добавить новый элемент в коллекцию";
    private ProductDirector productDirector;

    public AddCommand(String name, ProductCollection<T> collection, ProductDirector director) {
        super(collection, name);
        this.productDirector = director;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        productDirector.getBuilder().update(new Product());
        productDirector.build();
        Product product = productDirector.getBuilder().getProduct();
        super.getCollection().add(product);
    }
}
