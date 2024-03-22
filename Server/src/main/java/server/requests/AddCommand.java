package server.requests;

import collection.ProductCollection;
import exceptions.ExecuteException;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import server.database.DatabaseHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@Slf4j
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
    public <K extends Serializable> String execute(CommandArgs<K> arguments) throws ExecuteException {
        Optional<Product> optional = Optional.empty();
        for (K arg : arguments.getArgs()) {
            if (arg instanceof Product) {
                optional = Optional.of((Product) arg);
            }
        }
        DatabaseHandler handler = arguments.getHandler();
        Optional<Product> product = handler.addProduct(optional.orElseThrow(() -> new ExecuteException("Message isn't valid")), arguments.getUserId());
        return super.getCollection().add(product.orElseThrow(() -> new ExecuteException("Some problems with database, please repeat request")));
    }
}
