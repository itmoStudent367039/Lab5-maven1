package org.example.builders;

import java.time.LocalDateTime;

public class BuildChecker {
    private static final String INTEGER = "[0-9]{1,9}";
    private static final String MEASURE_ENUM = "[1-4]";
    private static final String LONG_VALUE = "^-?[0-9]{1,17}$";
    private static final String DOUBLE_VALUE = "^[-+]?[0-9]*\\.?[0-9]+$";
    private static final String COLOR_ENUM = "[1-6]";
    private static final String COUNTRY_ENUM = "[1-3]";
    private static final String UUID_FORMAT = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
    public static boolean checkId(String id) {
        return id.matches(UUID_FORMAT);
    }
    public static boolean checkProductName(String name) {
        return !name.isEmpty();
    }
    public static boolean checkProductPrice(String price) {
        return price.matches(INTEGER);
    }
    public static boolean checkMeasure(String measure) {
        return measure.matches(MEASURE_ENUM);
    }
    public static boolean checkLocalDate(LocalDateTime date) {
        return date.getYear() >= 1996 && date.getYear() <= 2023;
    }
    public static boolean checkXCoordinate(String x) {
            return x.matches(LONG_VALUE);
    }
    public static boolean checkHeight(String height) {
        return height.matches(INTEGER);
    }
    public static boolean checkYCoordinate(String y) {
        boolean value = y.length() < 18 && y.matches(DOUBLE_VALUE);
        if (value) {
            Double y1 = Double.parseDouble(y);
            return (y1.compareTo(Double.valueOf("628")) == 0) || (y1.compareTo(Double.valueOf("628")) < 0);
        }
        return false;
    }
    public static boolean checkColor(String color) {
        return color.matches(COLOR_ENUM);
    }
    public static boolean checkDoubleCoordinate(String coordinate) {
        return coordinate.length() < 18 && coordinate.matches(DOUBLE_VALUE);
    }
    public static boolean checkLocationName(String name) {
        return !name.isEmpty() && name.length() <= 871;
    }
    public static boolean checkCountry(String country) {
        return country.matches(COUNTRY_ENUM);
    }
}
