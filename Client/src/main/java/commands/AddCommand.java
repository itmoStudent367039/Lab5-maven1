package commands;

import exceptions.ExecuteException;
import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Slf4j
public class AddCommand extends Command {
    private final Client<Serializable> receiver;
    private final String name = "add";

    public AddCommand(Client<Serializable> receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        try {
            Response response = receiver.send(CommandType.ADD, new Serializable[]{receiver.createProduct(args)});
            TypeResponse type = response.getResponse();
            if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
                System.out.println("Bad connection. Enter \"connect\" to connect with server");
            } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
                System.out.println(response.getMessage());
            }
        } catch (ValidException | InvocationTargetException | IllegalAccessException e) {
            throw new ExecuteException(e);
        }
    }
    @Override
    public String getName() {
        return name;
    }
}
