package org.example.collection;

import org.example.objects.Product;

import java.util.Collection;

public interface ProductCollection<T extends Collection<Product>> extends TypeCollection<T, Product> {
    void show();
}
