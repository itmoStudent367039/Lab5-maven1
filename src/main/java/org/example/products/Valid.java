package org.example.products;

import org.example.exceptions.ValidException;

public interface Valid {
    boolean isValid() throws ValidException;
}
