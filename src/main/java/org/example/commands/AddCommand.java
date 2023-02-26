package org.example.commands;

import org.example.builders.ArgsProductBuilder;
import org.example.collection.ProductCollection;
import org.example.director.ProductDirector;
import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.Collection;

public class AddCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add {element}: добавить новый элемент в коллекцию";
    private ProductDirector productDirector;

    public AddCommand(String name, ProductCollection<T> collection, ProductDirector director) {
        super(collection, name);
        this.productDirector = director;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String... args) {
        try {
            Product product = buildProduct(productDirector, args);
            super.getCollection().add(product);
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
    }

    static Product buildProduct(ProductDirector productDirector, String[] args) throws ValidException {
        if (args.length == 16) {
            ProductDirector productDirector1 = new ProductDirector(new ArgsProductBuilder(args));
            productDirector1.getBuilder().update(new Product());
            productDirector1.build();
            return productDirector1.getBuilder().getProduct();
        } else {
            productDirector.getBuilder().update(new Product());
            productDirector.build();
            return productDirector.getBuilder().getProduct();
        }
    }
}
