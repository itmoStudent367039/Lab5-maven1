package org.example.products;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.exceptions.ValidException;


import java.util.Objects;
import java.util.UUID;

@Data
@JsonAutoDetect
public class Product implements Valid, Comparable<Product> {
    private UUID id;
    private String name;
    private Coordinates coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private java.time.ZonedDateTime creationDate;
    private int price;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    public Product() {
        super();
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
        int eps = o.price - this.getPrice();
        if (eps != 0) {
            return eps;
        } else {
            return this.name.toUpperCase().compareTo(o.getName().toUpperCase());
        }
    }
}
