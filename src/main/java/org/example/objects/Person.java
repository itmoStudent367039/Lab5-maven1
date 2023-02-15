package org.example.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class Person {
    private String name;
    private Long height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public Country getNationality() {
        return nationality;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Long getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
