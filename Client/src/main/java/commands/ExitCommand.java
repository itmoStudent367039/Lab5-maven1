package commands;

import java.io.Serializable;

public class ExitCommand extends Command {
    private String name = "exit";
    private final Client<Serializable> client;

    public ExitCommand(Client<Serializable> client) {
        this.client = client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String ... args) {
        client.exit();
    }
}
