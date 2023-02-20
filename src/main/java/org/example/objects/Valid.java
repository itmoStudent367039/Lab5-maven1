package org.example.objects;

import org.example.exceptions.ValidException;

public interface Valid {
    boolean isValid() throws ValidException;
}
