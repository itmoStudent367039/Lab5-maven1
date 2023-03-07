package org.example.commands;

import lombok.extern.slf4j.Slf4j;
import org.example.builders.*;
import org.example.collection.ProductCollection;
import org.example.exceptions.ValidException;
import org.example.products.Person;
import org.example.products.Product;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class AddCommand<T extends Collection<Product>> extends Command<T, Product> {
    private final String description = "add {element}: добавить новый элемент в коллекцию";
    private final String name = "add";

    public AddCommand(ProductCollection<T> collection) {
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
    public void execute(String... args) {
        try {
            Product product = buildProduct(args);
            super.getCollection().add(product);
        } catch (ValidException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            System.out.println("Uncorrect input");
        }
    }

    /**
     * Вот здесь логика, если execute_script, потому что там комманда
     * вводиться с аргументами ,addIfMax - тоже самое
     */
    static Product buildProduct(String[] args) throws ValidException, InvocationTargetException, IllegalAccessException {
            List<Method> personMethods = Arrays.stream(PersonBuilder.class.getDeclaredMethods())
                    .filter(method -> !Objects.isNull(method.getAnnotation(Order.class)))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(Order.class).value()))
                    .collect(Collectors.toList());
            List<Method> productsMethods = Arrays.stream(ArgsProductBuilder.class.getDeclaredMethods())
                    .filter(method -> !Objects.isNull(method.getAnnotation(Order.class)))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(Order.class).value()))
                    .collect(Collectors.toList());
            int sumArgsOfMethods = countValueOfParameters(productsMethods) + countValueOfParameters(personMethods);
            if (sumArgsOfMethods == args.length) {
                return buildProductWithArgs(productsMethods, personMethods, args);
            } else {
                ProductConsoleBuilder builder = new ProductConsoleBuilder(new Product()) {{
                    setId();
                    setName();
                    setCoordinates();
                    setCreationDate();
                    setPrice();
                    setUnitOfMeasure();
                    setOwner();
                }};
            return builder.getProduct();
        }
    }
    private static Product buildProductWithArgs(List<Method> productMethods, List<Method> personMethods, String[] args) throws ValidException, InvocationTargetException, IllegalAccessException {
        int count = 0;
        PersonBuilder personBuilder = new PersonBuilder();
        for (Method method: personMethods) {
            if (method.getParameterCount() == 1) {
                method.invoke(personBuilder, args[count]);
                count++;
            } else if (method.getParameterCount() == 4) {
                method.invoke(personBuilder, args[count], args[count + 1], args[count + 2], args[count + 3]);
                count = count + 4;
            }
        }
        Person owner = personBuilder.getPerson();
        ArgsProductBuilder argsProductBuilder = new ArgsProductBuilder();
        for (Method method: productMethods) {
            if (method.getParameterCount() == 1) {
                method.invoke(argsProductBuilder, args[count]);
                count++;
            } else if (method.getParameterCount() == 2) {
                method.invoke(argsProductBuilder, args[count], args[count + 1]);
                count = count + 2;
            }
        }
        argsProductBuilder.setOwner(owner);
        return argsProductBuilder.getProduct();
    }
    private static int countValueOfParameters(List<Method> list) {
        int count = 0;
        for (Method method : list) {
            count = count + method.getParameterCount();
        }
        return count;
    }
}
