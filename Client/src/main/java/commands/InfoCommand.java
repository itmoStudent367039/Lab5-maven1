package commands;


import exceptions.ExecuteException;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;

public class InfoCommand extends Command {
    private final String name = "info";
    private final Client<Serializable> client;

    public InfoCommand(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        Response response = client.send(CommandType.INFO, null);
        TypeResponse type = response.getResponse();
        if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
            System.out.println("Bad connection. Enter \"connect\" to connect with server");
        } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
            System.out.println(response.getMessage());
        }
    }
}
