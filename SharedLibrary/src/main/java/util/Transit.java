package util;

import java.io.Serializable;
import java.net.SocketAddress;

public class Transit<T extends Serializable> implements Serializable {
    private final CommandType type;
    private T[] object;
    private final String userName;
    private final String password;

    private SocketAddress client;

    public Transit(CommandType type, T[] object, String userName, String password) {
        this.type = type;
        this.object = object;
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public SocketAddress getClient() {
        return client;
    }

    public void setClient(SocketAddress client) {
        this.client = client;
    }

    public CommandType getCommand() {
        return type;
    }

    public T[] getObject() {
        return object;
    }
}
