import application.Application;
import application.Authorization;
import builders.ProductDirector;
import commands.*;
import products.SimpleProductValidator;

import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class CollectionRunner {
    public static void main(String[] args) throws IOException {
        ProductDirector productDirector = new ProductDirector(new SimpleProductValidator());
        Authorization authorization = new Authorization();
        ResourceBundle bundle = ResourceBundle.getBundle("clientProperties");
        String host = bundle.getString("hostName");
        int port = Integer.parseInt(bundle.getString("port"));
        Client<Serializable> receiver = new Client<>(productDirector, host, port);
        try {
            authorization.run(receiver);
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        CommandEditor manager = new CommandEditor() {{
            addCommand(new AddCommand(receiver));
            addCommand(new ExitCommand(receiver));
            addCommand(new ShowCommand(receiver));
            addCommand(new ClearCommand(receiver));
            addCommand(new HeadCommand(receiver));
            addCommand(new InfoCommand(receiver));
            addCommand(new HelpCommand(receiver));
            addCommand(new HistoryCommand(receiver));
            addCommand(new RemoveByIdCommand(receiver));
            addCommand(new UpdateById(receiver));
            addCommand(new PrintOwnersCommand(receiver));
            addCommand(new CountLessMeasure(receiver));
            addCommand(new GroupElementsByNameCommand(receiver));
            addCommand(new AddIfMaxCommand(receiver));
            addCommand(new ExecuteScriptCommand(receiver, this));
            addCommand(new ShowPendingRequests(receiver));
            addCommand(new SendPendingRequest(receiver));
            addCommand(new InfinityRequest(receiver));
            addCommand(new CheckConnection(receiver, authorization));
        }};
        Application application = new Application(manager);
        application.run();
    }
}