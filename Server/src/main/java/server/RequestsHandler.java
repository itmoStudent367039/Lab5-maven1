package server;

import exceptions.ExecuteException;
import products.Product;
import server.database.DatabaseHandler;
import server.requests.Command;
import server.requests.CommandArgs;
import util.Transit;

import java.io.Serializable;
import java.util.*;

public class RequestsHandler<T extends Collection<Product>> {
    private final Map<String, Command<T, Product>> requestsMap = new HashMap<>();

    public Map<String, Command<T, Product>> getCommandMap() {
        return requestsMap;
    }

    public void addCommand(Command<T, Product> command) {
        requestsMap.put(command.getName(), command);
    }

    public String execute(Transit<? extends Serializable> transit, int userId, DatabaseHandler handler) throws ExecuteException {
        Command<T, Product> command = requestsMap.get(transit.getCommand().getName());
        CommandArgs<Serializable> args = new CommandArgs<>(transit.getObject(), userId, handler);
        return command.execute(args);
    }
}
