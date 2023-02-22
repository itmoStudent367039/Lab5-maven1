package org.example.collection;

import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.*;

public class ProductQueue implements ProductCollection<Queue<Product>> {
    private final Queue<Product> collection;
    public ProductQueue(Collection<Product> list) {
        collection = new PriorityQueue<>();
        addAllElementsOfCollection(list);
    }
    public void addElement(Product product) {
        collection.add(product);
    }

    @Override
    public void add(Product element) {
        if (!collection.contains(element)) {
            collection.add(element);
        }
    }

    public void clear() {
        collection.clear();
    }

    @Override
    public void info() {

    }

    @Override
    public void update(int id) {

    }

    @Override
    public void removeById(int id) {

    }

    @Override
    public void head() {

    }

    public void show() {
        for (Product product : collection) {
            System.out.println(product.getOwner());
        }
    }
    private void addAllElementsOfCollection(Collection<Product> list) {
        for (Product product: list) {
            try {
                if (product.isValid()) {
                    collection.add(product);
                }
            } catch (ValidException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }
}
