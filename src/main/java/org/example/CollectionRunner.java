package org.example;

import org.example.application.Application;
import org.example.builders.ProductConsoleBuilder;
import org.example.collection.ProductCollection;
import org.example.collection.ProductQueue;
import org.example.commands.*;
import org.example.director.ProductDirector;
import org.example.io.JsonReader;
import org.example.products.Product;
import org.example.util.DataUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

public class CollectionRunner {
    public static void main(String[] args) {
        DataUtil util = DataUtil.getInstance();
        File homeFile = util.getFile();
        JsonReader<Product> reader = new JsonReader<>(homeFile, Product[].class);
        ProductCollection<Queue<Product>> collection = new ProductQueue(reader.getElementsAsList(), homeFile);
        ProductDirector productDirector = new ProductDirector(new ProductConsoleBuilder());
        CommandEditor manager = new CommandEditor() {{
            addCommand(new AddCommand<>("add", collection, productDirector));
            addCommand(new ExitCommand<>("exit"));
            addCommand(new ShowCommand<>("show", collection));
            addCommand(new ClearCommand<>("clear", collection));
            addCommand(new HeadCommand<>("head", collection));
            addCommand(new InfoCommand<>("info", collection));
            addCommand(new HelpCommand<>("help", collection, this));
            addCommand(new HistoryCommand<>("history", collection, this));
            addCommand(new RemoveByIdCommand<>("remove_by_id", collection));
            addCommand(new UpdateById<>("update_by_id", collection, productDirector));
            addCommand(new PrintOwnersCommand<>("print_owners", collection));
            addCommand(new CountLessMeasure<>("count_less_measure", collection));
            addCommand(new GroupElementsByNameCommand<>("group_products_by_name", collection));
            addCommand(new AddIfMaxCommand<>("add_if_max", collection, productDirector));
        }};
        Application application = new Application(manager);
        application.run();
    }
}