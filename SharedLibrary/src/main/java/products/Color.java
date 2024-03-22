package products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Color implements Serializable {
    BLACK(1, "Black(1)"),
    WHITE(2, "White(2)"),
    YELLOW(3, "Yellow(3)"),
    BROWN(4, "Brown(4)"),
    GREEN(5, "Green(5)"),
    RED(6, "Red(6)");
    private final int number;
    private final String name;
    Color(int number, String name) {
        this.number = number;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Color getColorByNumber(int i) {
        for (Color color: Color.values()) {
            if (color.number == i) {
                return color;
            }
        }
        return null;
    }
    public static Color getColorByName(String name) {
        for (Color color: Color.values()) {
            if (color.name.equals(name)) {
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
