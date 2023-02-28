package org.example.commands;

import org.example.collection.TypeCollection;

import java.util.Collection;

public abstract class Command<T extends Collection<E>, E> {
    private TypeCollection<?, E> collection;
    public Command(TypeCollection<?, E> collection) {
        this.collection = collection;
    }

    public Command() {
    }

    public String getDescription() {
        return "default-description";
    }
    public String getName() {
        return "default-name";
    }
    public TypeCollection<?, E> getCollection() {
        return collection;
    }

    public abstract void execute(String ... args);
}
