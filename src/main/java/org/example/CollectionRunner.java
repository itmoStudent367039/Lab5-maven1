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
            addCommand(new AddCommand<>(collection));
            addCommand(new ExitCommand<>());
            addCommand(new ShowCommand<>(collection));
            addCommand(new ClearCommand<>(collection));
            addCommand(new HeadCommand<>(collection));
            addCommand(new InfoCommand<>(collection));
            addCommand(new HelpCommand<>(collection, this));
            addCommand(new HistoryCommand<>(collection, this));
            addCommand(new RemoveByIdCommand<>(collection));
            addCommand(new UpdateById<>(collection));
            addCommand(new PrintOwnersCommand<>(collection));
            addCommand(new CountLessMeasure<>(collection));
            addCommand(new GroupElementsByNameCommand<>(collection));
            addCommand(new AddIfMaxCommand<>(collection));
            addCommand(new SaveCommand<>(collection, writer));
            addCommand(new ExecuteScriptCommand<>(this));
        }};
        Application application = new Application(manager);
        application.run();
    }
}