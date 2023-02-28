package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ArgsProductBuilder implements ProductBuilder {
    private final Product product = new Product();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setName(String name) throws ValidException {
        if (!BuildChecker.checkProductName(name)) {
            throw new ValidException("Uncorrect input (product's name)");
        }
        product.setName(name);
    }

    @Override
    public void setCoordinates(String x, String y) throws ValidException {
        if (!BuildChecker.checkXCoordinate(x) || !BuildChecker.checkYCoordinate(y)) {
            throw new ValidException("Uncorrect input (product's coordinates)");
        }
        product.setCoordinates(new Coordinates(Long.parseLong(x), Double.parseDouble(y)));
    }

    @Override
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
    public void setPrice(String price) throws ValidException {
        if (!BuildChecker.checkProductPrice(price)) {
            throw new ValidException("Uncorrect input (product's price)");
        }
        product.setPrice(Integer.parseInt(price));
    }

    @Override
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
