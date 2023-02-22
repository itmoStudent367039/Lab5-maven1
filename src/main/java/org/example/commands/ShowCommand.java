package org.example.commands;


import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class ShowCommand <T extends Collection<Product>> extends Command<T, Product> {
    public static final String description = "dfdsf";
    public static final String syntax = "djjfndsf";
    public ShowCommand(String name, ProductCollection<T> myCollection) {
        super(myCollection, name);
    }
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void execute() {

    }
}
