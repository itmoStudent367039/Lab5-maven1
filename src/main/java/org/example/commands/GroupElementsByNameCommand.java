package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class GroupElementsByNameCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "group_products_by_name: сгрупировать элементы коллекции по значению поля name, вывести количество элементов в каждой группе";

    public GroupElementsByNameCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        super.getCollection().groupElementsByName();
    }
}
