package org.example.director;

import org.example.builders.ProductBuilder;
import org.example.exceptions.ValidException;
import org.example.products.Product;

public class ProductDirector {
    private ProductBuilder builder;
    public ProductDirector(ProductBuilder builder) {
        this.builder = builder;
    }

    public ProductBuilder getBuilder() {
        return builder;
    }
    public void setBuilder(ProductBuilder builder) {
        this.builder = builder;
    }

    public void build() throws ValidException {
        builder.setName();
        builder.setCoordinates();
        builder.setCreationDate();
        builder.setPrice();
        builder.setUnitOfMeasure();
        builder.setOwner();
    }
}
