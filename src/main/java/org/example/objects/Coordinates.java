package org.example.objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.exceptions.ValidException;

import java.util.Objects;
@Data
public class Coordinates implements Valid {
    private Long x;
    private double y;

    @Override
    public boolean isValid() {
        return !Objects.isNull(x) && y <= 622;
    }
}
