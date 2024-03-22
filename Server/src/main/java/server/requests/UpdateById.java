package server.requests;

import collection.ProductCollection;
import exceptions.ExecuteException;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import server.database.DatabaseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

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
    public <K extends Serializable> String execute(CommandArgs<K> arguments) throws ExecuteException {
        Optional<Product> product = Optional.empty();
        Optional<Integer> prodId = Optional.empty();
        for (K arg : arguments.getArgs()) {
            if (arg instanceof Product) {
                product = Optional.of((Product) arg);
            } else if (arg instanceof Integer) {
                prodId = Optional.of((Integer) arg);
            }
        }

        DatabaseHandler handler = arguments.getHandler();
        int userId = arguments.getUserId();
        try {
            if (handler.checkById(prodId.orElseThrow(() -> new ExecuteException("You enter empty id")))) {

                if (handler.isOwner(prodId.get(), userId)) {
                    handler.updateProduct(product.orElseThrow(() -> new ExecuteException("You product isn't valid")), prodId.get());
                    super.getCollection().removeById(prodId.get());
                    Product p = product.get();
                    p.setId(prodId.get());
                    super.getCollection().add(p);
                    log.info(String.format("Element with id %d was updated", prodId.get()));
                    return String.format("Element with id %d was updated", prodId.get());
                } else {
                    log.warn(String.format("Permission denied, user - %d try to delete element %d", userId, prodId.get()));
                    return "Permission denied (You aren't a owner of this product)";
                }

            } else {
                return "Product with this id wasn't found";
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }
}
