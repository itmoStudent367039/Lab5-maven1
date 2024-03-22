package commands;


import builders.BuildChecker;
import exceptions.ExecuteException;
import util.CommandType;
import util.Response;
import util.TypeResponse;

import java.io.IOException;
import java.io.Serializable;


public class CountLessMeasure extends Command {
    private final Client<Serializable> client;
    private final String name = "count_less_measure";

    public CountLessMeasure(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        if (validate(args)) {
            Response response = client.send(CommandType.COUNT_LESS_MEASURE, new Serializable[]{Integer.parseInt(args[0])});
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
            return BuildChecker.checkMeasure(args[0]);
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
