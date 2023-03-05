package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.Person;
import org.example.products.Product;

public interface ProductBuilder {
    void setName(String name) throws ValidException;
    void setId(String id) throws ValidException;
    void setCoordinates(String x, String y) throws ValidException;
    void setCreationDate(String date, String time) throws ValidException;
    void setPrice(String price) throws ValidException;
    void setUnitOfMeasure(String measure) throws ValidException;
    void setOwner(Person person) throws ValidException;
    Product getProduct() throws ValidException;
}
