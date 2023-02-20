package org.example.objects;

import lombok.Data;

import java.util.Objects;
@Data
public class Location implements Valid {
    private Double x;
    private Double y;
    private Long z;
    private String name;

    @Override
    public boolean isValid() {
        return !Objects.isNull(x) &&
                !Objects.isNull(y) &&
                !Objects.isNull(z) &&
                !Objects.isNull(name) && name.length() < 871;
    }
}
