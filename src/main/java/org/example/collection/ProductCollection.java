package org.example.collection;

import org.example.io.JsonWriter;
import org.example.products.Product;

import java.io.File;
import java.util.Collection;
import java.util.Queue;

public interface ProductCollection<T extends Collection<Product>> extends TypeCollection<T, Product> {
}
