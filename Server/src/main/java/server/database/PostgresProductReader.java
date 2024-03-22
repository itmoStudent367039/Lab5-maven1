package server.database;

import exceptions.ValidException;
import lombok.extern.slf4j.Slf4j;
import products.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class PostgresProductReader extends PostgresReader<Product> {
    private static final String TAKE_ALL_PRODUCTS_REQUESTS = "select * from products\n" +
            "    join persons on products.owner_id = persons.id\n" +
            "    join locations on persons.location_id = locations.id\n" +
            "    join coordinates on products.coordinate_id = coordinates.id;";
    @Override
    public List<Product> readObjects(Connection connection) throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement jStatement = connection.prepareStatement(TAKE_ALL_PRODUCTS_REQUESTS);
        ResultSet result = jStatement.executeQuery();

        while (result.next()) {
            try {
                Product product = getProduct(result);
                products.add(product);
            } catch (ValidException | IllegalArgumentException e) {
                log.error(e.getMessage(), e);
            }
        }

        jStatement.close();
        return products;
    }
    private Product getProduct(ResultSet set) throws SQLException, ValidException {
        int id = set.getInt("prod_id");
        String prod_name = set.getString("prod_name");
        ZonedDateTime time = ZonedDateTime.ofInstant(set.getTimestamp("creation_date").toLocalDateTime().toInstant(ZoneOffset.UTC), ZoneId.of("UTC"));
        int price = set.getInt("price");
        UnitOfMeasure measure = UnitOfMeasure.getMeasureByName(set.getString("measure"));
        String personName = set.getString("person_name");
        int personHeight = set.getInt("person_height");
        Color eyeColor = Color.getColorByName(set.getString("eye_color"));
        Color hairColor = Color.getColorByName(set.getString("eye_color"));
        Country nationality = Country.getCountryByName(set.getString("nationality"));
        Double loc_x = set.getDouble("loc_x");
        Double loc_y = set.getDouble("loc_y");
        Long loc_z = set.getLong("loc_z");
        String loc_name = set.getString("loc_name");
        Long coor_x = set.getLong("coor_x");
        double coor_y = set.getDouble("coor_y");
        Validator<Product> validator = new SimpleProductValidator();
        Coordinates coordinates = new Coordinates(coor_x, coor_y);
        Location location = new Location(loc_x, loc_y, loc_z, loc_name);
        Person person = new Person(personName, personHeight, eyeColor, hairColor, nationality, location);
        Product product = new Product(id, prod_name, coordinates, time, price, measure, person);
        validator.checkElement(product);
        return product;
    }

}
