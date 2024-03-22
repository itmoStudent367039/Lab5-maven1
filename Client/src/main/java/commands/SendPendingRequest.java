package commands;


import builders.BuildChecker;
import exceptions.ExecuteException;
import util.Response;

import java.io.IOException;
import java.io.Serializable;

public class SendPendingRequest extends Command {
    private final Client<Serializable> client;

    public SendPendingRequest(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public void execute(String... args) {
        if (validate(args)) {
            try {
                Response response = client.sendPendingRequest(Integer.parseInt(args[0]));
                System.out.println(response.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.printf("Request with id - %s wasn't found \n", args[0]);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Uncorrect input");
        }
    }

    @Override
    public String getName() {
        return "send_pending_request";
    }

    private boolean validate(String[] args) {
        if (args.length != 1) {
            return false;
        }
        return args[0].matches(BuildChecker.INTEGER);
    }
}
