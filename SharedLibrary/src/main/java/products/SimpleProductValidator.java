package products;

import exceptions.ValidException;

import java.util.Objects;

public class SimpleProductValidator implements Validator<Product> {
    @Override
    public boolean checkElement(Product object) throws ValidException {
        boolean check = !Objects.isNull(object.getName()) &&
                !object.getName().isEmpty() &&
                checkCoordinates(object.getCoordinates()) &&
                !Objects.isNull(object.getCreationDate()) &&
                object.getPrice() > 0 &&
                !Objects.isNull(object.getUnitOfMeasure()) &&
                checkPerson(object.getOwner());
        if (!check) {
            throw new ValidException("Element isn't valid");
        }
        return true;
    }

    public boolean checkLocation(Location location) {
        return !Objects.isNull(location) &&
                !Objects.isNull(location.getX()) &&
                !Objects.isNull(location.getY()) &&
                !Objects.isNull(location.getZ()) &&
                !Objects.isNull(location.getName()) && location.getName().length() < 871;
    }

    public boolean checkCoordinates(Coordinates coordinates) {
        return !Objects.isNull(coordinates) &&
                !Objects.isNull(coordinates.getX()) &&
                coordinates.getY() <= 622;
    }

    public boolean checkPerson(Person person) {
        return !Objects.isNull(person) &&
                !Objects.isNull(person.getName()) &&
                !person.getName().isEmpty() &&
                person.getHeight() > 0 &&
                !Objects.isNull(person.getEyeColor()) &&
                !Objects.isNull(person.getHairColor()) &&
                !Objects.isNull(person.getNationality()) &&
                checkLocation(person.getLocation());
    }
}
