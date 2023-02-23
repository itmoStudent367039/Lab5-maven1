package org.example.builders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BuildChecker {
    public static boolean checkProductName(String name) {
        return !name.isEmpty();
    }
    public static boolean checkProductPrice(String price) {
        return price.matches("[0-9]{1,9}") && !price.equals("0");
    }
    public static boolean checkMeasure(String measure) {
        return measure.matches("[1-4]");
    }
    public static boolean checkLocalDate(LocalDateTime date) {
        return date.getYear() >= 1996 && date.getYear() <= 2023;
    }
    public static boolean checkXCoordinate(String x) {
            return x.length() < 18 && x.matches("^-?[0-9]+$");
    }
    public static boolean checkHeight(String x) {
        return x.length() < 10 && !x.equals("0") && x.matches("[0-9]{1,9}");
    }
    public static boolean checkYCoordinate(String y) {
        boolean value = y.length() < 18 && y.matches("^[-+]?[0-9]*\\.?[0-9]+$");
        if (value) {
            Double y1 = Double.parseDouble(y);
            return y1.compareTo(Double.valueOf("628")) == 0 || y1.compareTo(Double.valueOf("628")) < 0;
        }
        return false;
    }
    public static boolean checkColor(String color) {
        return color.matches("[1-6]");
    }
    public static boolean checkDoubleCoordinate(String coordinate) {
        return coordinate.length() < 18 && coordinate.matches("^[-+]?[0-9]*\\.?[0-9]+$");
    }
    public static boolean checkLocationName(String name) {
        return !name.isEmpty() && name.length() <= 871;
    }
}
