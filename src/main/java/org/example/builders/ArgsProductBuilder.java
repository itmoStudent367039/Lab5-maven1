package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ArgsProductBuilder implements ProductBuilder {
    private String[] args;
    private Product product;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ArgsProductBuilder(String... args) {
        this.args = args;
    }

    @Override
    public void setName() throws ValidException {
        String name = args[0];
        if (!BuildChecker.checkProductName(name)) {
            throw new ValidException("Uncorrect input (product's name)");
        }
        product.setName(name);
    }

    @Override
    public void setCoordinates() throws ValidException {
        String x = args[1];
        String y = args[2];
        if (!BuildChecker.checkXCoordinate(x) || !BuildChecker.checkYCoordinate(y)) {
            throw new ValidException("Uncorrect input (product's coordinates)");
        }
        product.setCoordinates(new Coordinates(Long.parseLong(x), Double.parseDouble(y)));
    }

    @Override
    public void setCreationDate() throws ValidException {
        String datetime = String.format("%s %s", args[3], args[4]);
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
    public void setPrice() throws ValidException {
        String price = args[5];
        if (!BuildChecker.checkProductPrice(price)) {
            throw new ValidException("Uncorrect input (product's price)");
        }
        product.setPrice(Integer.parseInt(price));
    }

    @Override
    public void setUnitOfMeasure() throws ValidException {
        String measure = args[6];
        if (!BuildChecker.checkMeasure(measure)) {
            throw new ValidException("Uncorrect input (measure-enum)");
        }
        product.setUnitOfMeasure(UnitOfMeasure.getMeasureByNumber(Integer.parseInt(measure)));
    }

    @Override
    public void setOwner() throws ValidException {
        String name = args[7];
        String height = args[8];
        String eyes = args[9];
        String hair = args[10];
        String country = args[11];
        String x = args[12];
        String y = args[13];
        String z = args[14];
        String locationName = args[15];
        if (!BuildChecker.checkProductName(name) || !BuildChecker.checkHeight(height) || !BuildChecker.checkColor(eyes) || !BuildChecker.checkColor(hair) || !BuildChecker.checkCountry(country)
                || !BuildChecker.checkDoubleCoordinate(x) || !BuildChecker.checkDoubleCoordinate(y) || !BuildChecker.checkXCoordinate(z) || !BuildChecker.checkLocationName(locationName)) {
            throw new ValidException("Uncorrect owner's fields");
        }
        product.setOwner(new Person(name, Integer.parseInt(height), Color.getColorByNumber(Integer.parseInt(eyes)), Color.getColorByNumber(Integer.parseInt(hair)),
                Country.getCountryByNumber(Integer.parseInt(country)), new Location(Double.parseDouble(x), Double.parseDouble(y), Long.parseLong(z), locationName)));
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void update(Product product) {
        this.product = product;
    }
}
