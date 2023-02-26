package org.example.commands;

import org.example.products.Product;

import java.util.Collection;

public class ExitCommand<T extends Collection<Product>> extends Command<T, Product> {
    private String description = "exit: завершить программу (без сохранения в файл)";

    @Override
    public String getDescription() {
        return description;
    }

    public ExitCommand(String name) {
        super(name);
    }
    @Override
    public void execute(String ... args) {
        System.out.println("Good Bye!");
        System.exit(0);
    }
}
