package server.newServer;

import products.Product;
import server.RequestsHandler;
import server.database.DatabaseHandler;

import java.util.Queue;

public class WorkerParameters {
    private final RequestsHandler<Queue<Product>> handler;
    private final DatabaseHandler dbHandler;
    private final CustomerRegistrar registrar;
    private final ServerEventQueue queue;

    public WorkerParameters(RequestsHandler<Queue<Product>> handler, DatabaseHandler dbHandler, CustomerRegistrar registrar, ServerEventQueue queue) {
        this.handler = handler;
        this.dbHandler = dbHandler;
        this.registrar = registrar;
        this.queue = queue;
    }

    public RequestsHandler<Queue<Product>> getHandler() {
        return handler;
    }

    public DatabaseHandler getDbHandler() {
        return dbHandler;
    }

    public CustomerRegistrar getRegistrar() {
        return registrar;
    }

    public ServerEventQueue getQueue() {
        return queue;
    }
}
