package org.example.products;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Objects;
@Data
@JsonAutoDetect
public class Coordinates implements Valid {
    private Long x;
    private double y;
    public Coordinates(Long x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    @Override
    public boolean isValid() {
        return !Objects.isNull(x) && y <= 622;
    }
}
