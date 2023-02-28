package org.example.builders;

import org.example.exceptions.ValidException;
import org.example.products.Color;
import org.example.products.Country;
import org.example.products.Location;
import org.example.products.Person;

public class PersonBuilder {
    private final Person person = new Person();

    public Person getPerson() throws ValidException {
        if (!person.isValid()) {
            throw new ValidException("Person isn't valid");
        }
        return person;
    }

    public void setName(String name) throws ValidException {
        if (!BuildChecker.checkProductName(name)) {
            throw new ValidException("Uncorrect input (owner's name)");
        }
        person.setName(name);
    }

    public void setHeight(String height) throws ValidException {
        if (!BuildChecker.checkHeight(height)) {
            throw new ValidException("Uncorrect input (owner's height)");
        }
        person.setHeight(Integer.parseInt(height));
    }

    public void setEyesColor(String eyes) throws ValidException {
        if (!BuildChecker.checkColor(eyes)) {
            throw new ValidException("Uncorrect input (owner's eyes)");
        }
        person.setEyeColor(Color.getColorByNumber(Integer.parseInt(eyes)));
    }

    public void setHairColor(String hair) throws ValidException {
        if (!BuildChecker.checkColor(hair)) {
            throw new ValidException("Uncorrect input (owner's hair)");
        }
        person.setHairColor(Color.getColorByNumber(Integer.parseInt(hair)));
    }

    public void setNationality(String nationality) throws ValidException {
        if (!BuildChecker.checkCountry(nationality)) {
            throw new ValidException("Uncorrect input (owner's nationality)");
        }
        person.setNationality(Country.getCountryByNumber(Integer.parseInt(nationality)));
    }

    public void setLocation(String x, String y, String z, String locationName) throws ValidException {
        if (!BuildChecker.checkDoubleCoordinate(x) || !BuildChecker.checkDoubleCoordinate(y)
                || !BuildChecker.checkXCoordinate(z) || !BuildChecker.checkLocationName(locationName)) {
            throw new ValidException("Uncorrect owner's location");
        }
        person.setLocation(new Location(Double.parseDouble(x), Double.parseDouble(y), Long.parseLong(z), locationName));
    }
}
