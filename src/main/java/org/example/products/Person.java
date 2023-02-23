package org.example.products;

import lombok.Data;

import java.util.Objects;
@Data
public class Person implements Valid, Comparable<Person> {
    private String name;
    private int height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Person(String name, int height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person() {
    }

    @Override
    public boolean isValid() {
        return !Objects.isNull(name) &&
                !name.isEmpty() &&
                height > 0 &&
                !Objects.isNull(eyeColor) &&
                !Objects.isNull(hairColor) &&
                !Objects.isNull(nationality) &&
                !Objects.isNull(location) &&
                location.isValid();
    }

    @Override
    public int compareTo(Person o) {
        return this.name.toUpperCase().compareTo(o.getName().toUpperCase()) * -1;
    }
}
