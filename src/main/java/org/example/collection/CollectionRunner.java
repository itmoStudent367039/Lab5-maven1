package org.example.collection;

import org.example.builders.ProductBuilder;
import org.example.builders.ProductConsoleBuilder;
import org.example.commands.AddCommand;
import org.example.commands.CommandEditor;
import org.example.director.ProductDirector;
import org.example.io.JsonReader;
import org.example.products.Product;
import org.example.util.DataUtil;

import java.util.Queue;

public class CollectionRunner {
    public static void main(String[] args) {
        DataUtil util = DataUtil.getInstance();
        JsonReader<Product> reader = new JsonReader<>(util.getFile(), Product[].class);
        ProductCollection<Queue<Product>> collection = new ProductQueue(reader.getElementsAsList());
        ProductDirector productDirector = new ProductDirector(ProductConsoleBuilder.getInstance());
        CommandEditor manager = new CommandEditor() {{
            addCommand(new AddCommand<>("add", collection, productDirector));
        }};
        manager.executeAdd();
        collection.show();
    }
}