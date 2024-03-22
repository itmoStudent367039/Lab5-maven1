package collection;


import products.Product;

import java.util.Collection;

public interface ProductCollection<T extends Collection<Product>> extends TypeCollection<T, Product> {
}
