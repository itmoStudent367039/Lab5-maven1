package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.Coordinates;
import org.example.products.Person;
import org.example.products.Product;
import org.example.products.UnitOfMeasure;

import java.time.ZonedDateTime;

public interface ProductBuilder {
    void setName() throws ValidException;
    void setCoordinates() throws ValidException;
    void setCreationDate() throws ValidException;
    void setPrice() throws ValidException;
    void setUnitOfMeasure() throws ValidException;
    void setOwner() throws ValidException;
    Product getProduct() throws ValidException;
    void update(Product product);
}
