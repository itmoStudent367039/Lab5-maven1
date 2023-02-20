package org.example.collection;

import org.example.exceptions.ValidException;
import org.example.objects.Product;

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
    public void clear() {
        collection.clear();
    }
    public void show() {

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
