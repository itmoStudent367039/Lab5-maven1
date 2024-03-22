package commands;


import exceptions.ExecuteException;

public abstract class Command {

    public abstract void execute(String ... args) throws ExecuteException;

    public String getName() {
        return "default-name";
    }
}
