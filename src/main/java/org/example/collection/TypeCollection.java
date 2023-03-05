package org.example.collection;

import org.example.io.JsonWriter;
import org.example.products.Product;
import org.example.products.UnitOfMeasure;

import java.io.File;
import java.util.Collection;
import java.util.Queue;
import java.util.UUID;

public interface TypeCollection<T extends Collection<E>, E> {
    void add(E element);
    void clear();
    void info();
    boolean removeById(UUID id);
    E head();
    void show();
    int size();
    boolean checkElementById(UUID id);
    E getElementById(UUID id);
    void printOwners();
    void countLessMeasure(UnitOfMeasure unitOfMeasure);
    void groupElementsByName();
    void save(JsonWriter<E> writer);
    T getCollection();
    File getFile();
}
