package server.requests;



import collection.ProductCollection;
import products.Product;
import server.RequestsHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class HelpCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final RequestsHandler<T> handler;
    private final String name = "help";
    private final String description = "help: вывести справку по доступным командам";

    public HelpCommand(ProductCollection<T> collection, RequestsHandler<T> handler) {
        super(collection);
        this.handler = handler;
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
        return handler.getCommandMap()
                .values()
                .stream()
                .filter(command -> !command.getName().equals(""))
                .map(Command::getDescription)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
