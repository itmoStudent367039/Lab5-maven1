package commands;


import exceptions.ExecuteException;

import java.io.Serializable;

public class ShowPendingRequests extends Command {
    private final Client<Serializable> client;

    public ShowPendingRequests(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        client.showPendingRequests();
    }

    @Override
    public String getName() {
        return "show_pending_requests";
    }
}
