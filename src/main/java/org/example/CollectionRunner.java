package org.example;

import org.example.application.Application;
import org.example.collection.ProductCollection;
import org.example.collection.ProductQueue;
import org.example.commands.*;
import org.example.io.JsonReader;
import org.example.io.JsonWriter;
import org.example.products.Product;
import org.example.util.DataUtil;

import java.io.File;
import java.util.*;
/**
 * ProductDirector - директор для BuildProduct, с помощью него получаю объекты;
 *
 * CommandEditor - хранит в себе Map<Command.getName(), Command),
 * метод execute() - для выполнения;
 *
 * Application - для запуска всей проги в бесконечном цикле, в нем организована логика чтения
 * с консоли, вызывается в этом цикле метод execute(String[] userInput)
 *
 * P.S. постарался расписать ту часть кода, которую сложно прочитать -> *Не только в этом классе,
 * не судите строго пожалуйста
 *
 *
 *
 */
public class CollectionRunner {
    public static void main(String[] args) {
        DataUtil util = DataUtil.getInstance();
        File homeFile = util.getFile();
        JsonReader<Product> reader = new JsonReader<>(homeFile, Product[].class);
        ProductCollection<Queue<Product>> collection = new ProductQueue(reader.getElementsAsList(), homeFile);
        JsonWriter<Product> writer = new JsonWriter<>();
        CommandEditor manager = new CommandEditor() {{
            addCommand(new AddCommand<>("add", collection));
            addCommand(new ExitCommand<>("exit"));
            addCommand(new ShowCommand<>("show", collection));
            addCommand(new ClearCommand<>("clear", collection));
            addCommand(new HeadCommand<>("head", collection));
            addCommand(new InfoCommand<>("info", collection));
            addCommand(new HelpCommand<>("help", collection, this));
            addCommand(new HistoryCommand<>("history", collection, this));
            addCommand(new RemoveByIdCommand<>("remove_by_id", collection));
            addCommand(new UpdateById<>("update_by_id", collection));
            addCommand(new PrintOwnersCommand<>("print_owners", collection));
            addCommand(new CountLessMeasure<>("count_less_measure", collection));
            addCommand(new GroupElementsByNameCommand<>("group_products_by_name", collection));
            addCommand(new AddIfMaxCommand<>("add_if_max", collection));
            addCommand(new SaveCommand<>("save", collection, writer));
            addCommand(new ExecuteScriptCommand<>("execute_script", this));
        }};
        Application application = new Application(manager);
        application.run();
    }
}