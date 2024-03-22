package server.requests;

import collection.TypeCollection;
import products.Product;
import server.RequestsHandler;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final RequestsHandler<T> handler;

    public HistoryCommand(TypeCollection<T, Product> collection, RequestsHandler<T> handler) {
        super(collection);
        this.handler = handler;
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "history: вывести последние 11 команд (без их аргументов)";
    }


//    @Override
//    public <K extends Serializable> String execute(CommandArgs<K> arguments) {
//        List<String> history = handler.getHistory();
//        if (history.size() > 11) {
//            StringBuilder buffer = new StringBuilder();
//            for (int i = history.size() - 12; i < history.size() - 1; i++) {
//                buffer.append(history.get(i)).append(System.lineSeparator());
//            }
//            return buffer.toString();
//        } else {
//            if (history.isEmpty()) {
//                return "Empty commands' history";
//            } else {
//                return history.stream()
//                        .collect(Collectors.joining(System.lineSeparator()));
//            }
//        }
//    }
}
