package builders;


import exceptions.ValidException;
import products.Person;
import products.Product;
import products.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductDirector {
    private final List<Method> personMethods;
    private final Validator<Product> productValidator;
    private final List<Method> productsMethods;

    {
        personMethods = getMethodsByOrder(PersonBuilder.class);
        productsMethods = getMethodsByOrder(ArgsProductBuilder.class);
    }

    public ProductDirector(Validator<Product> validator) {
        this.productValidator = validator;
    }
    public Product buildProduct(String ... args) throws ValidException, InvocationTargetException, IllegalAccessException {
        int sumArgsOfMethods = countValueOfParameters(productsMethods) + countValueOfParameters(personMethods);
        if (sumArgsOfMethods == args.length) {
            return buildProductWithArgs(productsMethods, personMethods, args);
        } else {
            ProductConsoleBuilder builder = new ProductConsoleBuilder(new Product()) {{
                setName();
                setCoordinates();
                setCreationDate();
                setPrice();
                setUnitOfMeasure();
                setOwner();
            }};
            return builder.getProduct(productValidator);
        }
    }

    private <T> List<Method> getMethodsByOrder(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> !Objects.isNull(method.getAnnotation(Order.class)))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(Order.class).value()))
                .collect(Collectors.toList());
    }

    private Product buildProductWithArgs(List<Method> productMethods, List<Method> personMethods, String[] args) throws ValidException, InvocationTargetException, IllegalAccessException {
        int count = 0;
        PersonBuilder personBuilder = new PersonBuilder();
        for (Method method : personMethods) {
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
        for (Method method : productMethods) {
            if (method.getParameterCount() == 1) {
                method.invoke(argsProductBuilder, args[count]);
                count++;
            } else if (method.getParameterCount() == 2) {
                method.invoke(argsProductBuilder, args[count], args[count + 1]);
                count = count + 2;
            }
        }
        argsProductBuilder.setOwner(owner);
        return argsProductBuilder.getProduct(productValidator);
    }

    private int countValueOfParameters(List<Method> list) {
        int count = 0;
        for (Method method : list) {
            count = count + method.getParameterCount();
        }
        return count;
    }
}
