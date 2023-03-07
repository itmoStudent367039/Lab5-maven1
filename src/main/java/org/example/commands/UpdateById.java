package org.example.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.builders.ProductConsoleBuilder;
import org.example.collection.ProductCollection;
import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.Collection;
import java.util.UUID;
@Slf4j
public class UpdateById<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "update id {element}: обновить значения элемента из коллекции по его id";
    private final String name = "update";

    public UpdateById(ProductCollection<T> collection) {
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
    public void execute(String ... args) {
        if (args.length != 1) {
            System.out.println("Empty input");
            return;
        }
        try {
            UUID id = UUID.fromString(args[0]);
            if (super.getCollection().removeById(id)) {
                Product product = new Product();
                product.setId(id);
                ProductConsoleBuilder consoleBuilder = new ProductConsoleBuilder(product) {{
                    setName();
                    setCoordinates();
                    setCreationDate();
                    setPrice();
                    setUnitOfMeasure();
                    setOwner();
                }};
                super.getCollection().add(consoleBuilder.getProduct());
            } else {
                System.out.println("Element with this id wasn't found");
            }
        } catch (IllegalArgumentException | ValidException e) {
            log.error(e.getMessage(), e);
            System.out.println("Uncorrect input");
        }
    }
}
