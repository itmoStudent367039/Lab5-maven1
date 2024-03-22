package commands;

import exceptions.ExecuteException;
import products.*;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class InfinityRequest extends Command {
    private final Client<Serializable> client;

    public InfinityRequest(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        Product product = new Product(2, "name",new Coordinates(1L, 2), ZonedDateTime.now(), 2,
                UnitOfMeasure.GRAMS, new Person("asd", 2, Color.WHITE, Color.WHITE,Country.GERMANY, new Location(2.0, 2.9, 3L, "fd")));
        int i = 0;
        while (i < 20) {
            Response response = client.send(CommandType.ADD,new Serializable[]{product});
            TypeResponse type = response.getResponse();
            if (type == TypeResponse.EMPTY_REGISTRATION || type == TypeResponse.EMPTY_CONNECTION_WITH_SERVER) {
                System.out.println("Bad connection. Enter \"connect\" to connect with server");
            } else if (response.getResponse() != TypeResponse.BAD_RESPONSE){
                System.out.println(response.getMessage());
            }
            i++;
        }
    }

    @Override
    public String getName() {
        return "inf";
    }
}
