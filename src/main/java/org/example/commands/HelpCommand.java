package org.example.commands;


import org.example.collection.ProductCollection;
import org.example.products.Product;

import java.util.Collection;

public class HelpCommand <T extends Collection<Product>> extends Command<T, Product> {
    private CommandEditor director;
    public static final String syntax = "jfds";
    public static final String description = "dsfsdfsdfsd";
    public HelpCommand(String name, ProductCollection<T> collection, CommandEditor commandDirector) {
        super(collection, name);
        this.director = commandDirector;
    }

    @Override
    public String getName() {
        return super.getName();
    }
    public CommandEditor getDirector() {
        return director;
    }

    @Override
    public void execute() {

    }
}
