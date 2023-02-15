package org.example.objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public class Product {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private java.time.ZonedDateTime creationDate;
    private Long price;
    private UnitOfMeasure unitOfMeasure;
    private Person owner;

    public Integer getId() {
        return id;
    }

    public Person getOwner() {
        return owner;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Long getPrice() {
        return price;
    }
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }
}
