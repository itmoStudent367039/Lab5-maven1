package org.example.commands;

import org.example.collection.ProductCollection;
import org.example.io.JsonWriter;
import org.example.products.Product;

import java.io.File;
import java.util.Collection;

public class SaveCommand<T extends Collection<Product>> extends Command<T, Product, String> {
    private File file;
    private final String description = "save: сохранить коллекцию в файл";
    public SaveCommand(String name, ProductCollection<T> collection, File file, JsonWriter writer) {
        super(collection, name);
        this.file = file;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String arg) {

    }
}
