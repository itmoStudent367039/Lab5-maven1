package org.example.commands;

import org.example.builders.ArgsProductBuilder;
import org.example.builders.ProductConsoleBuilder;
import org.example.collection.ProductCollection;
import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.Collection;

public class AddCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add {element}: добавить новый элемент в коллекцию";

    public AddCommand(String name, ProductCollection<T> collection) {
        super(collection, name);
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
            ArgsProductBuilder argsProductBuilder = new ArgsProductBuilder() {{
                setName(args[0]);
                setCoordinates(args[1], args[2]);
                setCreationDate(args[3], args[4]);
                setPrice(args[5]);
                setUnitOfMeasure(args[6]);
                setOwner(args[7], args[8], args[9], args[10], args[11], args[12], args[13], args[14], args[15]);
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
