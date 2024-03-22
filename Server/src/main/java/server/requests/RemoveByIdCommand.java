package server.requests;

import collection.ProductCollection;
import exceptions.ExecuteException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import server.database.DatabaseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class RemoveByIdCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "remove_by_id id: удалить элемент из коллекции по его id, который равен заданному";
    private final String name = "remove_by_id";

    public RemoveByIdCommand(ProductCollection<T> collection) {
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
        Optional<Integer> id = Optional.empty();
        for (K arg : arguments.getArgs()) {
            if (arg instanceof Integer) {
                id = Optional.of((Integer) arg);
            }
        }

        DatabaseHandler handler = arguments.getHandler();
        int userId = arguments.getUserId();

        try {
            if (handler.isOwner(id.orElseThrow(() -> new ExecuteException("Empty args for remove cmd")), userId)) {
                handler.removeProduct(id.get());
                if (super.getCollection().removeById(id.get())) {
                    log.info(String.format("%s was deleted successfully", id));
                    return "Delete successfully";
                } else {
                    log.warn("Element with this id wasn't found");
                    return "Element with this id wasn't found";
                }
            } else {
                log.warn(String.format("Permission denied, user - %d try to delete element %d", userId, id.get()));
                return "Permission denied (You aren't a owner of this product)";
            }
        } catch (SQLException e) {
            log.error(e.getSQLState(), e);
            return e.getMessage();
        }
    }
}
