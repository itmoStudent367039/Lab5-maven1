package org.example.products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Color {
    BLACK(1, "Black(1)"),
    WHITE(2, "White(2)"),
    YELLOW(3, "Yellow(3)"),
    BROWN(4, "Brown(4)"),
    GREEN(5, "Green(5)"),
    RED(6, "Red(6)");
    private int number;
    private String name;
    Color(int number, String name) {
        this.number = number;
        this.name = name;
    }
    public static Color getColorByNumber(int i) {
        for (Color color: Color.values()) {
            if (color.number == i) {
                return color;
            }
        }
        return null;
    }
    public static List<String> getColorsList() {
        List<String> colors = new ArrayList<>();
        for (Color color: Color.values()) {
            colors.add(color.name);
        }
        return colors;
    }
}
