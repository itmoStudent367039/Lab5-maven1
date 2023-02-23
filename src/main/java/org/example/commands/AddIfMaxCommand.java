package org.example.commands;

import org.example.collection.TypeCollection;
import org.example.director.ProductDirector;
import org.example.products.Product;

import java.util.Collection;

public class AddIfMaxCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "add_if_max: добавить новый элемент в коллекцию, если его значение наибольшего элемента в этой коллекции";
    private ProductDirector productDirector;

    public AddIfMaxCommand(String name, TypeCollection<?, Product> collection, ProductDirector productDirector) {
        super(collection, name);
        this.productDirector = productDirector;
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
        if (super.getCollection().size() == 0) {
            super.getCollection().add(product);
        }
    }
}
