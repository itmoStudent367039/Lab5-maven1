package builders;


import exceptions.ValidException;
import products.Person;
import products.Product;
import products.Validator;

public interface ProductBuilder {
    void setName(String name) throws ValidException;

    void setCoordinates(String x, String y) throws ValidException;

    void setCreationDate(String date, String time) throws ValidException;

    void setPrice(String price) throws ValidException;

    void setUnitOfMeasure(String measure) throws ValidException;

    void setOwner(Person person) throws ValidException;

    Product getProduct(Validator<Product> validator) throws ValidException;
}
