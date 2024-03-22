package server.requests;


import collection.ProductCollection;
import exceptions.ExecuteException;
import products.Product;
import products.UnitOfMeasure;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class CountLessMeasure<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "count_less_measure unitOfMeasure: вывести количество элементов, значение поля которых меньше заданного";
    private final String name = "count_less_measure";

    public CountLessMeasure(ProductCollection<T> collection) {
        super(collection);
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
    public <K extends Serializable> String execute(CommandArgs<K> arguments) throws ExecuteException {
        Optional<Integer> measure = Optional.empty();
        for (K arg : arguments.getArgs()) {
            if (arg instanceof Integer) {
                measure = Optional.of((Integer) arg);
            }
        }
        return super.getCollection()
                .countLessMeasure(UnitOfMeasure.getMeasureByNumber(measure.orElseThrow(() -> new ExecuteException("Empty args count_less_measure cmd"))));
    }

}
