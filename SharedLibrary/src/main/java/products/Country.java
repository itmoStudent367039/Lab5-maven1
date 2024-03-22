package products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Country implements Serializable {
    UNITED_KINGDOM(1, "UK(1)"),
    GERMANY(2, "Germany(2)"),
    NORTH_KOREA(3, "North Korea(3)");
    private final String name;
    private final int number;
    Country(int number, String name) {
        this.name = name;
        this.number = number;
    }
    public static Country getCountryByNumber(int i) {
        for (Country country: Country.values()) {
            if (country.number == i) {
                return country;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }
    public static Country getCountryByName(String name) {
        for (Country country: Country.values()) {
            if (country.name.equals(name)) {
                return country;
            }
        }
        return null;
    }
    public static List<String> getCountriesList() {
        List<String> countries = new ArrayList<>();
        for (Country country: Country.values()) {
            countries.add(country.name);
        }
        return countries;
    }
}
