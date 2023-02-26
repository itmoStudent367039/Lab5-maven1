package org.example.commands;

import org.example.builders.FileProductBuilder;
import org.example.collection.TypeCollection;
import org.example.director.ProductDirector;
import org.example.exceptions.ValidException;
import org.example.products.Product;

import java.util.Collection;

public class AddIfMaxCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add_if_max: добавить новый элемент в коллекцию, если его значение наибольшего элемента в этой коллекции";
    private ProductDirector productDirector;

    public AddIfMaxCommand(String name, TypeCollection<?, Product> collection, ProductDirector productDirector) {
        super(collection, name);
        this.productDirector = productDirector;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String... args) {
        try {
            Product product;
            if (args.length == 16) {
                ProductDirector productDirector1 = new ProductDirector(new FileProductBuilder(args));
                productDirector1.getBuilder().update(new Product());
                productDirector1.build();
                product = productDirector1.getBuilder().getProduct();
            } else {
                productDirector.getBuilder().update(new Product());
                productDirector.build();
                product = productDirector.getBuilder().getProduct();
            }
            if (super.getCollection().size() == 0) {
                super.getCollection().add(product);
                System.out.println("Product was add");
                return;
            }
            if (product.compareTo(super.getCollection().head()) < 0) {
                super.getCollection().add(product);
                System.out.println("Product was add");
            } else {
                System.out.println("Your product less than max of collection or equals it");
            }
        } catch (ValidException e) {
            System.out.println(e.getMessage());
        }
    }
}
