package org.example.products;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Objects;
@Data
@JsonAutoDetect
public class Location implements Valid {
    private Double x;
    private Double y;
    private Long z;
    private String name;

    public Location(Double x, Double y, Long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public Location() {
    }

    @Override
    public boolean isValid() {
        return !Objects.isNull(x) &&
                !Objects.isNull(y) &&
                !Objects.isNull(z) &&
                !Objects.isNull(name) && name.length() < 871;
    }
}
