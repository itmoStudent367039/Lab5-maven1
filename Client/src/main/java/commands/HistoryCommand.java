package commands;


import exceptions.ExecuteException;

import java.io.Serializable;

public class HistoryCommand extends Command {
    private final Client<Serializable> client;
    private final String name = "history";

    public HistoryCommand(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String... args) throws ExecuteException {
        System.out.println(client.getHistory());
        client.addHistory(name);
    }
}
