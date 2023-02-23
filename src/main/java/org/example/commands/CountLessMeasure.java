package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.exceptions.ValidException;
import org.example.products.Product;
import org.example.products.UnitOfMeasure;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class CountLessMeasure<T extends Collection<Product>> extends Command<T, Product, String> {
    private final String description = "count_less_measure unitOfMeasure: вывести количество элементов, значение поля которых меньше заданного";

    public CountLessMeasure(String name, ProductCollection<T> collection) {
        super(collection, name);
    }

    private boolean checkArg(String arg) {
        return !Objects.isNull(arg) && arg.matches("[1-4]");
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {
        if (checkArg(arg)) {
            int value = Integer.parseInt(arg);
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.getMeasureByNumber(value);
            super.getCollection().countLessMeasure(unitOfMeasure);
        } else {
            System.out.println("Uncorrect input");
        }
    }
}
