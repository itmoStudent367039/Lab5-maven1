package server.requests;

import collection.TypeCollection;
import exceptions.ExecuteException;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import server.database.DatabaseHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

@Slf4j
public class AddIfMaxCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add_if_max: добавить новый элемент в коллекцию, если его значение наибольшего элемента в этой коллекции";
    private final String name = "add_if_max";

    public AddIfMaxCommand(TypeCollection<?, Product> collection) {
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
        boolean isMax = super.getCollection().isMax(optional.orElseThrow(() -> new ExecuteException("Message isn't valid")));
        DatabaseHandler handler = arguments.getHandler();
        int userId = arguments.getUserId();
        if (isMax) {
            Optional<Product> product = handler.addProduct(optional.get(), userId);
            return super.getCollection().add(product.orElseThrow(() -> new ExecuteException("Problems with database connection")));
        } else {
            return "Product is less or equals max product";
        }
    }

}
