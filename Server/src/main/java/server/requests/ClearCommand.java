package server.requests;

import collection.ProductCollection;
import products.Product;
import server.database.DatabaseHandler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ClearCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "clear: очистить коллекцию";
    private final String name = "clear";

    public ClearCommand(ProductCollection<T> collection) {
        super(collection);
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
    public <K extends Serializable> String execute(CommandArgs<K> arguments) {
        try {
            DatabaseHandler handler = arguments.getHandler();
            int userId = arguments.getUserId();
            List<Integer> ids = handler.getProductsByUserId(userId);
            for (int id : ids) {
                handler.removeProduct(id);
                super.getCollection().removeById(id);
            }
            return "Your products were deleted";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
