package collection;


import io.JsonWriter;
import products.UnitOfMeasure;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public interface TypeCollection<T extends Collection<E>, E> {
    String add(E element);
    boolean isMax(E element);
    String info();
    boolean removeById(Integer id);
    E head();
    String show();
    int size();
    boolean checkElementById(Integer id);
    String printOwners();
    String countLessMeasure(UnitOfMeasure unitOfMeasure);
    String groupElementsByName();
    T getCollection();
}
