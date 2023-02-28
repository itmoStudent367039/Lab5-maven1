package org.example.commands;

import org.example.builders.ArgsProductBuilder;
import org.example.builders.PersonBuilder;
import org.example.builders.ProductConsoleBuilder;
import org.example.collection.ProductCollection;
import org.example.exceptions.ValidException;
import org.example.products.Person;
import org.example.products.Product;

import java.util.Collection;

public class AddCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add {element}: добавить новый элемент в коллекцию";
    private final String name = "add";

    public AddCommand(ProductCollection<T> collection) {
        super(collection);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String... args) {
        try {
            Product product = buildProduct(args);
            super.getCollection().add(product);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Вот здесь логика, если execute_script, потому что там комманда
     * вводиться с аргументами ,addIfMax - тоже самое
     */
    static Product buildProduct(String[] args) throws ValidException {
        if (args.length == 16) {
            PersonBuilder personBuilder = new PersonBuilder() {{
                setName(args[7]);
                setHeight(args[8]);
                setEyesColor(args[9]);
                setHairColor(args[10]);
                setNationality(args[11]);
                setLocation(args[12], args[13], args[14], args[15]);
            }};
            Person owner = personBuilder.getPerson();
            ArgsProductBuilder argsProductBuilder = new ArgsProductBuilder() {{
                setName(args[0]);
                setCoordinates(args[1], args[2]);
                setCreationDate(args[3], args[4]);
                setPrice(args[5]);
                setUnitOfMeasure(args[6]);
                setOwner(owner);
            }};
            return argsProductBuilder.getProduct();
        } else {
            ProductConsoleBuilder builder = new ProductConsoleBuilder(new Product()) {{
                setName();
                setCoordinates();
                setCreationDate();
                setPrice();
                setUnitOfMeasure();
                setOwner();
            }};
            return builder.getProduct();
        }
    }
}
