package org.example.commands;

public interface Command {
    String getName();

    void execute(Object... objects);
}
