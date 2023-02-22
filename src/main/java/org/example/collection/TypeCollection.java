package org.example.collection;

import java.util.Collection;

public interface TypeCollection<T extends Collection<E>, E> {
    void add(E element);
    void clear();
    void info();
    void update(int id);
    void removeById(int id);
    void head();
}
