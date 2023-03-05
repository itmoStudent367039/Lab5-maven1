package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;
@CountOfArgs(17)
public class ArgsProductBuilder implements ProductBuilder {
    private final Product product = new Product();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Order(1)
    public void setId(String id) throws ValidException {
        if (!BuildChecker.checkId(id)) {
            throw new ValidException("Uncorrect input (id-uuid)");
        }
        product.setId(UUID.fromString(id));
    }
    @Override
    @Order(2)
    public void setName(String name) throws ValidException {
        if (!BuildChecker.checkProductName(name)) {
            throw new ValidException("Uncorrect input (product's name)");
        }
        product.setName(name);
    }

    @Override
    @Order(3)
    public void setCoordinates(String x, String y) throws ValidException {
        if (!BuildChecker.checkXCoordinate(x) || !BuildChecker.checkYCoordinate(y)) {
            throw new ValidException("Uncorrect input (product's coordinates)");
        }
        product.setCoordinates(new Coordinates(Long.parseLong(x), Double.parseDouble(y)));
    }

    @Override
    @Order(4)
    public void setCreationDate(String date, String time) throws ValidException {
        String datetime = String.format("%s %s", date, time);
        ZonedDateTime creationDate;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(datetime, formatter);
            creationDate = localDateTime.atZone(ZoneId.systemDefault());
            product.setCreationDate(creationDate);
        } catch (DateTimeParseException e) {
            throw new ValidException(e.getMessage());
        }
    }

    @Override
    @Order(5)
    public void setPrice(String price) throws ValidException {
        if (!BuildChecker.checkProductPrice(price)) {
            throw new ValidException("Uncorrect input (product's price)");
        }
        product.setPrice(Integer.parseInt(price));
    }

    @Override
    @Order(6)
    public void setUnitOfMeasure(String measure) throws ValidException {
        if (!BuildChecker.checkMeasure(measure)) {
            throw new ValidException("Uncorrect input (measure-enum)");
        }
        product.setUnitOfMeasure(UnitOfMeasure.getMeasureByNumber(Integer.parseInt(measure)));
    }

    @Override
    public void setOwner(Person owner) {
        product.setOwner(owner);
    }

    @Override
    public Product getProduct() throws ValidException {
        return product.isValid() ? product : null;
    }
}
