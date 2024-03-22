package commands;

import builders.BuildChecker;
import exceptions.ExecuteException;
import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import products.Product;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

@Slf4j
public class UpdateById extends Command {
    private final String name = "update";
    private final Client<Serializable> client;

    public UpdateById(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        if (validate(args)) {
            try {
                Integer id = Integer.parseInt(args[0]);
                Product product = client.createProduct();
                product.setId(id);
                Response response = client.send(CommandType.UPDATE_BY_ID, new Serializable[]{id, product});
                TypeResponse type = response.getResponse();
                if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
                    System.out.println("Bad connection. Enter \"connect\" to connect with server");
                } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
                    System.out.println(response.getMessage());
                }
            } catch (ValidException | InvocationTargetException | IllegalAccessException e) {
                throw new ExecuteException(e);
            }
        } else {
            throw new ExecuteException("Uncorrect input");
        }
    }

    public boolean validate(String[] args) {
        if (args.length == 1) {
            return args[0].matches(BuildChecker.INTEGER);
        } else {
            return false;
        }
    }
}
