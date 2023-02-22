package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.collection.TypeCollection;
import org.example.products.Product;

import java.util.Collection;

public abstract class Command<T extends Collection<E>, E> {
    private TypeCollection<?, E> collection;
    private String name;
    public Command() {

    }
    public Command(TypeCollection<?, E> collection, String name) {
        this.collection = collection;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public TypeCollection<?, E> getCollection() {
        return collection;
    }

    public abstract void execute();
}
