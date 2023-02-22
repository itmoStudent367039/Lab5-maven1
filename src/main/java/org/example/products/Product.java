package org.example.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.example.exceptions.ValidException;

import java.util.Objects;
import java.util.UUID;

@Data
public class Product implements Valid, Comparable<Product>{
    @JsonIgnore
    private UUID id;
    private String name;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate;
    private int price;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    public Product() {
        super();
        id = UUID.randomUUID();
    }

    @Override
    public boolean isValid() throws ValidException {
        boolean isValid = !Objects.isNull(name) &&
                !name.isEmpty() &&
                !Objects.isNull(coordinates) &&
                !Objects.isNull(creationDate) &&
                price > 0 &&
                !Objects.isNull(unitOfMeasure) &&
                !Objects.isNull(owner) &&
                coordinates.isValid() && owner.isValid();
        if (!isValid) {
            throw new ValidException("Element isn't valid");
        }
        return isValid;
    }

    @Override
    public int compareTo(Product o) {
        return 0;
    }
}
