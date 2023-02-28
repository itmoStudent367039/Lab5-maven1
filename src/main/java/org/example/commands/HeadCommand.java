package org.example.commands;

import org.example.collection.TypeCollection;
import org.example.products.Product;

import java.util.Collection;
import java.util.Objects;

public class HeadCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "head: вывести первый элемент коллекции";
    private final String name = "head";

    public HeadCommand(TypeCollection<?, Product> collection) {
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
        Product product = super.getCollection().head();
        if (Objects.isNull(product)) {
            System.out.println("Collection is empty");
        } else {
            System.out.println(product);
        }
    }
}
