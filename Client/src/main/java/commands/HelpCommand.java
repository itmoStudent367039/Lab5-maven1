package commands;

import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.Serializable;

public class HelpCommand extends Command {
    private final String name = "help";
    private final Client<Serializable> client;

    public HelpCommand(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String... args) {
        Response response = client.send(CommandType.HELP, null);
        TypeResponse type = response.getResponse();
        if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
            System.out.println("Bad connection. Enter \"connect\" to connect with server");
        } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
            System.out.println(response.getMessage());
        }
    }
}
