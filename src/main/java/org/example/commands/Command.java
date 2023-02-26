package org.example.commands;

import org.example.collection.TypeCollection;

import java.util.Collection;

public abstract class Command<T extends Collection<E>, E> {
    private TypeCollection<?, E> collection;
    private final String name;
    public Command(TypeCollection<?, E> collection, String name) {
        this.collection = collection;
        this.name = name;
    }
    abstract public String getDescription();
    public Command(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public TypeCollection<?, E> getCollection() {
        return collection;
    }

    public abstract void execute(String ... args);
}
