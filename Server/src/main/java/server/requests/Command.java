package server.requests;


import collection.TypeCollection;
import exceptions.ExecuteException;

import java.io.Serializable;
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

    public <K extends Serializable> String execute(CommandArgs<K> args) throws ExecuteException {
        return "abstract realization";
    }
}
