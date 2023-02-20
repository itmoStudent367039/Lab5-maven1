package org.example.objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.example.exceptions.ValidException;

import java.util.Objects;
@Data
public class Person implements Valid {
    private String name;
    private int height;
    private Color eyeColor;
    private Color hairColor;
    private Country nationality;
    private Location location;


    @Override
    public boolean isValid() {
        return !Objects.isNull(name) &&
                !name.isEmpty() &&
                height > 0 &&
                !Objects.isNull(eyeColor) &&
                !Objects.isNull(hairColor) &&
                !Objects.isNull(nationality) &&
                !Objects.isNull(location);
    }
}
