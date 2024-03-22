package server.requests;


import collection.ProductCollection;
import products.Product;

import java.io.Serializable;
import java.util.Collection;

public class ShowCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private final String name = "show";

    public ShowCommand(ProductCollection<T> myCollection) {
        super(myCollection);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public <K extends Serializable> String execute(CommandArgs<K> argument) {
        return super.getCollection().show();
    }
}
