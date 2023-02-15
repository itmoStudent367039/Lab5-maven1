package org.example.collection;

import org.example.objects.Product;

import java.util.PriorityQueue;
import java.util.Queue;

public class MyCollection {
    private Queue<Product> collection = new PriorityQueue<>();
    public void addElement(Product product) {
        collection.add(product);
    }
    public void clear() {
        collection.clear();
    }
}
