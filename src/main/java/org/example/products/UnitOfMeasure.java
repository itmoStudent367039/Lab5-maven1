package org.example.products;

import java.util.ArrayList;
import java.util.List;

public enum UnitOfMeasure {
    METERS(1, "Meters(1)"),
    SQUARE_METERS(2, "Square meters(2)"),
    PCS(3, "Pcs(3)"),
    GRAMS(4, "Grams(4)");
    private String name;
    private int number;
    UnitOfMeasure(int number, String name) {
        this.name = name;
        this.number = number;
    }
    public static UnitOfMeasure getMeasureByNumber(int i) {
        for (UnitOfMeasure measure: UnitOfMeasure.values()) {
            if (measure.number == i) {
                return measure;
            }
        }
        return null;
    }
    public static List<String> getMeasuresList() {
        List<String> measures = new ArrayList<>();
        for (UnitOfMeasure measure: UnitOfMeasure.values()) {
            measures.add(measure.name);
        }
        return measures;
    }

}
