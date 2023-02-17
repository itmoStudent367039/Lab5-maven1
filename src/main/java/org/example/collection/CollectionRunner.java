package org.example.collection;

import org.example.commands.AddCommand;
import org.example.commands.CommandDirector;
import org.example.io.JsonReader;
import org.example.objects.Product;
import org.example.util.DataUtil;

import java.util.List;

public class CollectionRunner {
    public static void main(String[] args) {
        MyCollection collection = new MyCollection();
        CommandDirector manager = new CommandDirector() {{
            addCommand(new AddCommand("add", collection));
        }};
        DataUtil util = DataUtil.getInstance();
        JsonReader<Product> reader = new JsonReader<>(util.getFile(), Product[].class);
        List<Product> products = reader.getElementsAsList();
        products.stream().forEach(product -> System.out.println(product.getOwner().getName()));
    }
}