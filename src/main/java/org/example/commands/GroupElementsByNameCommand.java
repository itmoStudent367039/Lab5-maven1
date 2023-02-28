package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class GroupElementsByNameCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "group_products_by_name: сгрупировать элементы коллекции по значению поля name, вывести количество элементов в каждой группе";
    private final String name = "group_products_by_name";

    public GroupElementsByNameCommand(ProductCollection<T> collection) {
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
        super.getCollection().groupElementsByName();
    }
}
