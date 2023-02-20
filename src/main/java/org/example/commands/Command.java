package org.example.commands;

import org.example.collection.TypeCollection;

import java.util.Collection;

public abstract class Command<T extends Collection<E>, E> {
    protected TypeCollection<?, E> collection;
    protected E element;
    private String name;
    private String description;

    public TypeCollection<?, E> getCollection() {
        return collection;
    }


    public String getName() {
        return name;
    }
    String getDescription() {
        return description;
    }

    public abstract void execute(Object... objects);
}
