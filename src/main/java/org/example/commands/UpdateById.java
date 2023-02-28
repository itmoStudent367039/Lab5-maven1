package org.example.commands;

import org.example.builders.ProductConsoleBuilder;
import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

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
    /**
     * беру вверхушку, так как элементы по убыванию отсортированы(price, а если цены равны,
     * то по имени сортировка(алфавит -> убывание (a, b, c ....))
     */
    @Override
    public void execute(String ... args) {
        try {
            UUID id = UUID.fromString(args[0]);
            Product product = super.getCollection().getElementById(id);
            if (!Objects.isNull(product)) {
                new ProductConsoleBuilder(product) {{
                    setName();
                    setCoordinates();
                    setCreationDate();
                    setPrice();
                    setUnitOfMeasure();
                    setOwner();
                }};
            } else {
                System.out.println("Element with this id wasn't found");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
