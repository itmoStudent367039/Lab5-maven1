package org.example.builders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BuildChecker {
    private static String integer = "[0-9]{1,9}";
    private static String fromOneToFour = "[1-4]";
    private static String longValue = "^-?[0-9]{1,17}$";
    private static String doubleValue = "^[-+]?[0-9]*\\.?[0-9]+$";
    private static String fromOneToSix = "[1-6]";
    private static String fromOneToThree = "[1-3]";
    public static boolean checkProductName(String name) {
        return !name.isEmpty();
    }
    public static boolean checkProductPrice(String price) {
        return price.matches(integer);
    }
    public static boolean checkMeasure(String measure) {
        return measure.matches(fromOneToFour);
    }
    public static boolean checkLocalDate(LocalDateTime date) {
        return date.getYear() >= 1996 && date.getYear() <= 2023;
    }
    public static boolean checkXCoordinate(String x) {
            return x.matches(longValue);
    }
    public static boolean checkHeight(String height) {
        return height.matches(integer);
    }
    public static boolean checkYCoordinate(String y) {
        boolean value = y.length() < 18 && y.matches(doubleValue);
        if (value) {
            Double y1 = Double.parseDouble(y);
            return (y1.compareTo(Double.valueOf("628")) == 0) || (y1.compareTo(Double.valueOf("628")) < 0);
        }
        return false;
    }
    public static boolean checkColor(String color) {
        return color.matches(fromOneToSix);
    }
    public static boolean checkDoubleCoordinate(String coordinate) {
        return coordinate.length() < 18 && coordinate.matches(doubleValue);
    }
    public static boolean checkLocationName(String name) {
        return !name.isEmpty() && name.length() <= 871;
    }
    public static boolean checkCountry(String country) {
        return country.matches(fromOneToThree);
    }
}
