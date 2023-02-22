package org.example.director;

import org.example.builders.ProductBuilder;
import org.example.products.Product;

public class ProductDirector {
    private ProductBuilder builder;
    public ProductDirector(ProductBuilder builder) {
        this.builder = builder;
    }

    public ProductBuilder getBuilder() {
        return builder;
    }

    public void build() {
        builder.update(new Product());
        builder.setName();
        builder.setCoordinates();
        builder.setCreationDate();
        builder.setPrice();
        builder.setUnitOfMeasure();
        builder.setOwner();
    }
}
