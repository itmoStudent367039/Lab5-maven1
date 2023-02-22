package org.example.builders;

import org.example.products.Coordinates;
import org.example.products.Person;
import org.example.products.Product;
import org.example.products.UnitOfMeasure;

import java.time.ZonedDateTime;

public interface ProductBuilder {
    void setName();
    void setCoordinates();
    void setCreationDate();
    void setPrice();
    void setUnitOfMeasure();
    void setOwner();
    Product getProduct();
    void update(Product product);
}
