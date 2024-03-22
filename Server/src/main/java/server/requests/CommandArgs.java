package server.requests;

import server.database.DatabaseHandler;

import java.io.Serializable;

public class CommandArgs<T extends Serializable> {
    private final T[] args;
    private final int userId;
    private final DatabaseHandler handler;

    public CommandArgs(T[] args, int userId, DatabaseHandler handler) {
        this.args = args;
        this.userId = userId;
        this.handler = handler;
    }

    public T[] getArgs() {
        return args;
    }

    public int getUserId() {
        return userId;
    }

    public DatabaseHandler getHandler() {
        return handler;
    }
}
