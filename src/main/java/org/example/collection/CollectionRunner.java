package org.example.collection;

import org.example.commands.AddCommand;
import org.example.commands.CommandDirector;
import org.example.io.JsonReader;
import org.example.objects.Product;
import org.example.util.DataUtil;

import java.util.List;
import java.util.Queue;

public class CollectionRunner {
    public static void main(String[] args) {
        DataUtil util = DataUtil.getInstance();
        JsonReader<Product> reader = new JsonReader<>(util.getFile(), Product[].class);
        ProductCollection<Queue<Product>> collection = new ProductQueue(reader.getElementsAsList());
        CommandDirector manager = new CommandDirector() {{
            addCommand(new AddCommand("add", (ProductQueue) collection));
        }};
    }
}