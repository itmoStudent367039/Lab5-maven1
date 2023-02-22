package org.example.products;

import java.util.ArrayList;
import java.util.List;

public enum Country {
    UNITED_KINGDOM(1, "UK(1)"),
    GERMANY(2, "Germany(2)"),
    NORTH_KOREA(3, "North Korea(3)");
    private String name;
    private int number;
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
    public static List<String> getCountriesList() {
        List<String> countries = new ArrayList<>();
        for (Country country: Country.values()) {
            countries.add(country.name);
        }
        return countries;
    }
}
