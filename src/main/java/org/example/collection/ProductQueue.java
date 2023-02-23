package org.example.collection;

import org.example.exceptions.ValidException;
import org.example.products.Person;
import org.example.products.Product;
import org.example.products.UnitOfMeasure;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ProductQueue implements ProductCollection<Queue<Product>> {
    private final Queue<Product> collection;
    private final java.time.ZonedDateTime creationDate;
    private File homeFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");

    public ProductQueue(Collection<Product> list, File file) {
        collection = new PriorityQueue<>();
        creationDate = ZonedDateTime.now();
        addAllElementsOfCollection(list);
        this.homeFile = file;
    }

    public ProductQueue() {
        collection = new PriorityQueue<>();
        creationDate = ZonedDateTime.now();
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public void add(Product element) {
        if (!collection.contains(element)) {
            collection.add(element);
        } else {
            System.out.println("Element already exists");
        }
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public void info() {
        System.out.printf("Тип: %s \n" +
                "Дата инициализации: %s \n" +
                "Количество элементов: %d \n" +
                "Место хранения: %s \n", ProductQueue.class.getSimpleName(), creationDate.format(formatter), collection.size(), homeFile.getName());
    }

    @Override
    public boolean checkElementById(UUID id) {
        return collection.stream()
                .map(Product::getId)
                .anyMatch(uuid -> uuid.equals(id));
    }

    @Override
    public void removeById(UUID id) {
        if (collection.removeIf(product -> product.getId().equals(id))) {
            System.out.println("Product deleted successfully");
        } else {
            System.out.printf("Product with id: %s was not found");
        }
    }

    @Override
    public Product getElementById(UUID id) {
        return collection.stream()
                .filter(product -> product.getId().equals(id))
                .findAny().orElse(null);
    }

    @Override
    public void head() {
        System.out.println(collection.peek());
    }

    @Override
    public void countLessMeasure(UnitOfMeasure unitOfMeasure) {
        System.out.printf("Count of elements less than %s - %d \n", unitOfMeasure.toString(), collection.stream()
                .map(Product::getUnitOfMeasure)
                .filter(unitOfMeasure1 -> unitOfMeasure1.compareTo(unitOfMeasure) < 0).count());
    }

    @Override
    public void printOwners() {
        System.out.println(collection.stream()
                .map(Product::getOwner)
                .sorted(Person::compareTo)
                .map(Person::toString)
                .collect(Collectors.joining(System.lineSeparator())));
    }

    @Override
    public void show() {
        System.out.println(
                collection.stream()
                        .map(Product::toString)
                        .collect(Collectors.joining(System.lineSeparator())));
    }

    private void addAllElementsOfCollection(Collection<Product> list) {
        for (Product product : list) {
            try {
                if (product.isValid()) {
                    collection.add(product);
                }
            } catch (ValidException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
