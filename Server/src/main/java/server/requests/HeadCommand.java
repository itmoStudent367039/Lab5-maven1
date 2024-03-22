package server.requests;


import collection.TypeCollection;
import products.Product;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class HeadCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "head: вывести первый элемент коллекции";
    private final String name = "head";

    public HeadCommand(TypeCollection<?, Product> collection) {
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
    public <K extends Serializable> String execute(CommandArgs<K> arguments) {
        Product product = super.getCollection().head();
        if (Optional.ofNullable(product).isEmpty()) {
            return "Collection is empty";
        } else {
            return product.toString();
        }
    }
}
