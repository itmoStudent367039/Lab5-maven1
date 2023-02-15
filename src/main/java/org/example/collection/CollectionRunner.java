package org.example.collection;

import org.example.commands.AddCommand;
import org.example.commands.CommandDirector;
import org.example.commands.Type;
import org.example.objects.Coordinates;
import org.example.objects.Person;
import org.example.objects.Product;

import java.util.List;

public class CollectionRunner {
    public static void main(String[] args) throws Exception {
        MyCollection collection = new MyCollection();
        CommandDirector manager = new CommandDirector() {{
            addCommand(new AddCommand("add", collection));
        }};
        JsonReader reader = new JsonReader("/home/yestai/Рабочий стол/University/json_file");
        List<Product> coordinates = reader.getProducts();
    }
}