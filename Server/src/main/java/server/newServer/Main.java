package server.newServer;

import collection.ProductCollection;
import collection.ProductQueue;
import products.Product;
import products.SimpleProductValidator;
import products.Validator;
import server.RequestsHandler;
import server.database.DatabaseHandler;
import server.database.PostgresProductReader;
import server.requests.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println((e.getMessage()));
            System.exit(-1);
        }
        List<Product> products = null;
        String databaseInfo = null;
        DatabaseHandler dbHandler = null;
        ResourceBundle bundle = ResourceBundle.getBundle("serverResources", new Locale("UA"));
        String url = bundle.getString("url");
        String userName = bundle.getString("userName");
        String password = bundle.getString("password");
        String hostName = bundle.getString("hostName");
        int port = Integer.parseInt(bundle.getString("port"));
        try {
            dbHandler = new DatabaseHandler(url, userName, password);
            products = new PostgresProductReader().readObjects(dbHandler.getConnection());
            databaseInfo = dbHandler.getConnection().getCatalog();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        Validator<Product> validator = new SimpleProductValidator();
        ProductCollection<Queue<Product>> collection = new ProductQueue(products, databaseInfo, validator);
        RequestsHandler<Queue<Product>> handler = new RequestsHandler<>() {{
            addCommand(new AddCommand<>(collection));
            addCommand(new ShowCommand<>(collection));
            addCommand(new ClearCommand<>(collection));
            addCommand(new HeadCommand<>(collection));
            addCommand(new InfoCommand<>(collection));
            addCommand(new HelpCommand<>(collection, this));
            addCommand(new HistoryCommand<>(collection, this));
            addCommand(new RemoveByIdCommand<>(collection));
            addCommand(new UpdateById<>(collection));
            addCommand(new PrintOwnersCommand<>(collection));
            addCommand(new CountLessMeasure<>(collection));
            addCommand(new GroupElementsByNameCommand<>(collection));
            addCommand(new AddIfMaxCommand<>(collection));
        }};
        CustomerRegistrar registrar = new CustomerRegistrar();
        ServerEventQueue queue = new ServerEventQueue();
        WorkerParameters parameters = new WorkerParameters(handler, dbHandler, registrar, queue);
        new Server(hostName, port, parameters).run();
    }
}
