package collection;

import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import products.Person;
import products.Product;
import products.UnitOfMeasure;
import products.Validator;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Slf4j
public class ProductQueue implements ProductCollection<Queue<Product>> {
    private final Queue<Product> collection;
    private final Validator<Product> productValidator;
    private final ZonedDateTime creationDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    private final String databaseInfo;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public ProductQueue(Collection<Product> list, String clientInfo, Validator<Product> productValidator) {
        this.productValidator = productValidator;
        collection = new PriorityQueue<>();
        log.info("collection was initialized");
        list.forEach(this::add);
        creationDate = ZonedDateTime.now();
        this.databaseInfo = clientInfo;
    }

    @Override
    public int size() {
        readLock.lock();
        int size = collection.size();
        readLock.unlock();
        return size;
    }

    @Override
    public Queue<Product> getCollection() {
        return collection;
    }


    @Override
    public String add(Product element) {
        try {
            String message;
            if (productValidator.checkElement(element) && !checkElementById(element.getId())) {
                writeLock.lock();
                collection.add(element);
                writeLock.unlock();
                message = String.format("Product with id: %s was added to collection", element.getId().toString());
            } else {
                message = String.format("element: %s is already exists", element.getId().toString());
            }
            log.info(message);
            return message;
        } catch (ValidException e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }
    }

    @Override
    public boolean isMax(Product product) {
        if (collection.size() == 0) {
            return true;
        }
        return product.compareTo(head()) < 0;
    }

    @Override
    public String info() {
        return String.format("Тип: %s \n" +
                "Дата инициализации: %s \n" +
                "Количество элементов: %d \n" +
                "Место хранения: %s \n", ProductQueue.class.getSimpleName(), creationDate.format(formatter), collection.size(), databaseInfo);
    }

    @Override
    public boolean checkElementById(Integer id) {
        readLock.lock();
        boolean isExist = collection.stream()
                .map(Product::getId)
                .anyMatch(i -> i.equals(id));
        readLock.unlock();
        return isExist;
    }

    @Override
    public boolean removeById(Integer id) {
        writeLock.lock();
        readLock.lock();
        boolean isRemoved = collection.removeIf(product -> product.getId().equals(id));
        writeLock.unlock();
        readLock.unlock();
        return isRemoved;
    }


    @Override
    public Product head() {
        return collection.peek();
    }

    @Override
    public String countLessMeasure(UnitOfMeasure unitOfMeasure) {
        writeLock.lock();
        readLock.lock();
        String response = String.format("Count of elements less than %s - %d \n", unitOfMeasure.toString(), collection.stream()
                .map(Product::getUnitOfMeasure)
                .filter(measure -> measure.compareTo(unitOfMeasure) < 0).count());
        writeLock.unlock();
        readLock.unlock();
        return response;
    }

    @Override
    public String groupElementsByName() {
        if (size() == 0) {
            return "Collection is empty";
        } else {
            writeLock.lock();
            readLock.lock();
            String response = collection.stream()
                    .collect(Collectors.groupingBy(Product::getName, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .map(stringLongEntry -> (String.format("%s : %d \n", stringLongEntry.getKey(), stringLongEntry.getValue())))
                    .collect(Collectors.joining());
            writeLock.unlock();
            readLock.unlock();
            return response;
        }
    }

    @Override
    public String printOwners() {
        writeLock.lock();
        readLock.lock();
        String response = null;
        if (size() == 0) {
            response = "Collection is empty";
        } else {
            response =  collection.stream()
                    .map(Product::getOwner)
                    .sorted(Person::compareTo)
                    .map(Person::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
        }
        writeLock.unlock();
        readLock.unlock();
        return response;
    }

    @Override
    public String show() {
        writeLock.lock();
        readLock.lock();
        String response = null;
        if (size() == 0) {
            response = "Collection is empty";
        } else {
            if (false) {
                response = collection.stream()
                        .limit(100)
                        .map(Product::toString)
                        .collect(Collectors.joining(System.lineSeparator())) + String.format("\n And %d products more ....", collection.size() - 100);
            } else {
                response = collection.stream()
                        .map(Product::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
            }
        }
        writeLock.unlock();
        readLock.unlock();
        return response;
    }
}
