package org.example.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import org.example.collection.ProductCollection;
import org.example.io.JsonWriter;
import org.example.products.Product;

import java.io.File;
import java.util.Collection;

public class SaveCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "save: сохранить коллекцию в файл";
    private final String name = "save";
    private JsonWriter<Product> writer;
    public SaveCommand(ProductCollection<T> collection, JsonWriter<Product> writer) {
        super(collection);
        this.writer = writer;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute(String ... args) {
        super.getCollection().save(writer);
    }
}
