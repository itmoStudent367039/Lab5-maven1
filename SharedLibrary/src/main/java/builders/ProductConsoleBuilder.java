package builders;

import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import products.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

@Slf4j
public class ProductConsoleBuilder {
    private final Product product;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ProductConsoleBuilder(Product product) {
        this.product = product;
    }

    private String input() {
        System.out.print("> ");
        String line = null;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage(), e);
            System.exit(0);
        }
        return line;
    }
    public void setName() {
        String productName = null;
        while (Objects.isNull(productName)) {
            System.out.println("Enter product's name, P.S: not empty");
            String line = input().trim();
            if (BuildChecker.checkProductName(line)) {
                productName = line;
            } else {
                System.out.println("Uncorrect input");
            }
        }
        product.setName(productName);
    }

    public void setCoordinates() {
        Long x = null;
        String y = "";

        while (Objects.isNull(x)) {
            System.out.println("Enter product's coordinate x, P.S: long number");
            String lineX = input().trim();

            if (BuildChecker.checkXCoordinate(lineX)) {
                x = Long.parseLong(lineX);

                while (y.equals("")) {
                    System.out.println("Enter product's coordinate y, P.S: double number <= 622");
                    String lineY = input().trim();

                    if (BuildChecker.checkYCoordinate(lineY)) {
                        y = lineY;
                    } else {
                        System.out.println("Uncorrect input");
                    }
                }
            } else {
                System.out.println("Uncorrect input");
            }
        }
        this.product.setCoordinates(new Coordinates(x, Double.parseDouble(y)));
    }

    public void setCreationDate() {
        ZonedDateTime creationDate = null;
        LocalDateTime localDateTime;
        while (Objects.isNull(creationDate)) {
            System.out.println("Enter product's creation date, P.S: \"yyyy-MM-dd HH:mm:ss\"");
            try {
                localDateTime = LocalDateTime.parse(input().trim(), formatter);
                if (BuildChecker.checkLocalDate(localDateTime)) {
                    creationDate = localDateTime.atZone(ZoneId.systemDefault());
                } else {
                    System.out.println("Uncorrect year, it should be between 1996 and 2023");
                }
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        product.setCreationDate(creationDate);
    }

    public void setPrice() {
        int productPrice = 0;
        while (productPrice == 0) {
            System.out.println("Enter product's price, P.S: Integer, more than zero");
            String value = input().trim();
            if (BuildChecker.checkProductPrice(value)) {
                productPrice = Integer.parseInt(value);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        product.setPrice(productPrice);
    }

    public void setUnitOfMeasure() {
        UnitOfMeasure measure = null;
        while (Objects.isNull(measure)) {
            System.out.println("Choose a number, which matches with measure:");
            System.out.println(UnitOfMeasure.getMeasuresList());
            String value = input().trim();
            if (BuildChecker.checkMeasure(value)) {
                measure = UnitOfMeasure.getMeasureByNumber(Integer.parseInt(value));
            } else {
                System.out.println("Uncorrect input");
            }
        }
        product.setUnitOfMeasure(measure);
    }

    public void setOwner() {
        String name = null;
        int height = 0;
        Color eyes = null;
        Color hair = null;
        Country country = null;
        while (Objects.isNull(name)) {
            System.out.println("Enter product's owner name, P.S: not empty");
            String line = input();
            if (BuildChecker.checkProductName(line)) {
                name = line;
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (height == 0) {
            System.out.println("Enter product's owner height, P.S: integer more than zero");
            String line = input().trim();
            if (BuildChecker.checkHeight(line)) {
                height = Integer.parseInt(line);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(eyes)) {
            System.out.println("Choose the number - owner's eyes color");
            System.out.println(Color.getColorsList());
            String line = input().trim();
            if (BuildChecker.checkColor(line)) {
                eyes = Color.getColorByNumber(Integer.parseInt(line));
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(hair)) {
            System.out.println("Choose the number - owner's hair color");
            System.out.println(Color.getColorsList());
            String line = input().trim();
            if (BuildChecker.checkColor(line)) {
                hair = Color.getColorByNumber(Integer.parseInt(line));
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(country)) {
            System.out.println("Choose the number - owner's nationality");
            System.out.println(Country.getCountriesList());
            String line = input().trim();
            if (BuildChecker.checkCountry(line)) {
                country = Country.getCountryByNumber(Integer.parseInt(line));
            } else {
                System.out.println("Uncorrect input");
            }
        }
        Double x = null;
        Double y = null;
        Long z = null;
        String locationName = null;
        while (Objects.isNull(x)) {
            System.out.println("Enter owner's location x coordinate, P.S: double");
            String line = input().trim();
            if (BuildChecker.checkDoubleCoordinate(line)) {
                x = Double.parseDouble(line);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(y)) {
            System.out.println("Enter owner's location y coordinate, P.S: double");
            String line = input().trim();
            if (BuildChecker.checkDoubleCoordinate(line)) {
                y = Double.parseDouble(line);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(z)) {
            System.out.println("Enter owner's location z coordinate, P.S: long");
            String line = input().trim();
            if (BuildChecker.checkXCoordinate(line)) {
                z = Long.parseLong(line);
            } else {
                System.out.println("Uncorrect input");
            }
        }
        while (Objects.isNull(locationName)) {
            System.out.println("Enter owner's location name, P.S: not empty");
            String line = input();
            if (BuildChecker.checkLocationName(line)) {
                locationName = line;
            } else {
                System.out.println("Uncorrect input");
            }
        }
        this.product.setOwner(new Person(name, height, eyes, hair, country, new Location(x, y, z, locationName)));
    }

    public Product getProduct(Validator<Product> validator) throws ValidException {
        return validator.checkElement(product) ? product : null;
    }
}
