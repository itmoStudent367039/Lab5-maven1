package commands;

import builders.BuildChecker;
import exceptions.ExecuteException;
import lombok.extern.slf4j.Slf4j;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Slf4j
public class RemoveByIdCommand extends Command {
    private final String name = "remove_by_id";
    private final Client<Serializable> client;

    public RemoveByIdCommand(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        if (validate(args)) {
            Response response = client.send(CommandType.REMOVE_BY_ID, new Serializable[]{Integer.parseInt(args[0])});
            TypeResponse type = response.getResponse();
            if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
                System.out.println("Bad connection. Enter \"connect\" to connect with server");
            } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
                System.out.println(response.getMessage());
            }
        } else {
            throw new ExecuteException("Uncorrect input");
        }
    }

    private boolean validate(String[] args) {
        if (args.length == 1) {
            return args[0].matches(BuildChecker.INTEGER);
        } else {
            return false;
        }
    }
}
