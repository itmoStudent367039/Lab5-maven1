package org.example.collection;

import org.example.products.UnitOfMeasure;

import java.util.Collection;
import java.util.UUID;

public interface TypeCollection<T extends Collection<E>, E> {
    void add(E element);
    void clear();
    void info();
    void removeById(UUID id);
    void head();
    void show();
    int size();
    boolean checkElementById(UUID id);
    E getElementById(UUID id);
    void printOwners();
    void countLessMeasure(UnitOfMeasure unitOfMeasure);
}
