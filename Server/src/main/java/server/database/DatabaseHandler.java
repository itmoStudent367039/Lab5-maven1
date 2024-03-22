package server.database;

import lombok.extern.slf4j.Slf4j;
import products.*;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DatabaseHandler {
    private final Connection connection;
    private static final String REGISTER_USER = "INSERT INTO users (user_name, user_password) VALUES (?,?) RETURNING id";
    private static final String CHECK_USER_EXIST = "SELECT COUNT(*) FROM users WHERE user_name = ?";
    private static final String VALIDATE_USER = "SELECT COUNT(*) AS count FROM users WHERE user_name = ? AND user_password = ?";
    private static final String GET_USER_ID_BY_NAME = "SELECT id FROM users WHERE user_name = ?";
    private static final String ADD_PRODUCT_REQUEST = "INSERT INTO products (prod_name, coordinate_id, creation_date, price, measure, owner_id, user_owner)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING prod_id";
    private static final String ADD_PERSON_REQUEST = "INSERT INTO persons (person_name, person_height, eye_color, hair_color, nationality, location_id)" +
            "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String ADD_COORDINATES_REQUEST = "INSERT INTO coordinates (coor_x, coor_y) VALUES (?, ?) RETURNING id";
    private static final String ADD_LOCATION = "INSERT INTO locations (loc_x, loc_y, loc_z, loc_name) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String GET_PRODUCTS_BY_USER_ID = "SELECT prod_id FROM products WHERE user_owner = ?";
    private static final String GET_PERSON_BY_PRODUCT_ID = "SELECT owner_id FROM products WHERE prod_id = ?";
    private static final String GET_LOCATION_BY_PERSON_ID = "SELECT location_id FROM persons WHERE id = ?";
    private static final String REMOVE_PRODUCT_BY_ID = "DELETE FROM products WHERE prod_id = ?";
    private static final String REMOVE_PERSON_BY_ID = "DELETE FROM persons WHERE id = ?";
    private static final String GET_COORDINATES_BY_PRODUCTS_ID = "SELECT coordinate_id FROM products WHERE prod_id = ?";
    private static final String REMOVE_LOCATION_BY_ID = "DELETE FROM locations WHERE id = ?";
    private static final String REMOVE_COORDINATE_BY_ID = "DELETE FROM coordinates WHERE id = ?";
    private static final String CHECK_PRODUCT_OWNER  = "SELECT COUNT(*) FROM products WHERE prod_id = ? AND user_owner = ?";
    private static final String CHECK_PRODUCT_BY_ID = "SELECT EXISTS (SELECT (1) FROM products WHERE prod_id = ?)";
    private static final String UPDATE_LOCATION = "UPDATE locations SET loc_x = ?, loc_y = ?, loc_z = ?, loc_name = ? WHERE id = ?";
    private static final String UPDATE_COORDINATE = "UPDATE coordinates SET coor_x = ?, coor_y = ? WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET prod_name = ?, creation_date = ?," +
            " price = ?, measure = ? WHERE prod_id = ?";
    private static final String UPDATE_PERSON = "UPDATE persons SET person_name = ?, person_height = ?, eye_color = ?, hair_color = ?, nationality = ? WHERE id = ?";

    public DatabaseHandler(String url, String userName, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, userName, password);
    }

    public synchronized Optional<Product> addProduct(Product product, int owner) {
        String productName = product.getName();
        Coordinates coordinates = product.getCoordinates();
        ZonedDateTime creationDate = product.getCreationDate();
        int price = product.getPrice();
        UnitOfMeasure measure = product.getUnitOfMeasure();
        Person person = product.getOwner();
        Location location = person.getLocation();
        try {
            connection.setAutoCommit(false);
            connection.setSavepoint();

            PreparedStatement addCoordinates = connection.prepareStatement(ADD_COORDINATES_REQUEST, Statement.RETURN_GENERATED_KEYS);
            addCoordinates.setLong(1, coordinates.getX());
            addCoordinates.setDouble(2, coordinates.getY());
            addCoordinates.executeUpdate();
            ResultSet coordinateSet = addCoordinates.getGeneratedKeys();
            coordinateSet.next();
            int coordinateId = coordinateSet.getInt(1);
            addCoordinates.close();

            PreparedStatement addLocation = connection.prepareStatement(ADD_LOCATION, Statement.RETURN_GENERATED_KEYS);
            addLocation.setDouble(1, location.getX());
            addLocation.setDouble(2, location.getY());
            addLocation.setLong(3, location.getZ());
            addLocation.setString(4, location.getName());
            addLocation.executeUpdate();
            ResultSet locationSet = addLocation.getGeneratedKeys();
            locationSet.next();
            int locationId = locationSet.getInt(1);
            addLocation.close();

            PreparedStatement addPerson = connection.prepareStatement(ADD_PERSON_REQUEST, Statement.RETURN_GENERATED_KEYS);
            addPerson.setString(1, person.getName());
            addPerson.setInt(2, person.getHeight());
            addPerson.setObject(3, person.getEyeColor().getName(), Types.OTHER);
            addPerson.setObject(4, person.getHairColor().getName(), Types.OTHER);
            addPerson.setObject(5, person.getNationality().getName(), Types.OTHER);
            addPerson.setInt(6, locationId);
            addPerson.executeUpdate();
            ResultSet personSet = addPerson.getGeneratedKeys();
            personSet.next();
            int personId = personSet.getInt(1);
            addPerson.close();

            PreparedStatement addProduct = connection.prepareStatement(ADD_PRODUCT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            addProduct.setString(1, productName);
            addProduct.setInt(2, coordinateId);
            addProduct.setTimestamp(3, Timestamp.from(creationDate.toInstant()));
            addProduct.setInt(4, price);
            addProduct.setObject(5, measure.getName(), Types.OTHER);
            addProduct.setInt(6, personId);
            addProduct.setInt(7, owner);
            addProduct.executeUpdate();
            ResultSet productSet = addProduct.getGeneratedKeys();
            productSet.next();
            int product_id = productSet.getInt(1);
            addProduct.close();

            connection.commit();
            connection.setAutoCommit(true);
            product.setId(product_id);
        } catch (SQLException e) {
            log.error(e.getSQLState(), e);
            return Optional.empty();
        }
        return Optional.of(product);
    }

    public void updateProduct(Product product, int prodId) throws SQLException {
        Person owner = product.getOwner();
        Location location = owner.getLocation();
        Coordinates coordinates = product.getCoordinates();

        PreparedStatement getCoordinateId = connection.prepareStatement(GET_COORDINATES_BY_PRODUCTS_ID);
        getCoordinateId.setInt(1, prodId);
        ResultSet set = getCoordinateId.executeQuery();
        set.next();
        int coordinateId = set.getInt(1);
        getCoordinateId.close();

        PreparedStatement getPerson = connection.prepareCall(GET_PERSON_BY_PRODUCT_ID);
        getPerson.setInt(1, prodId);
        ResultSet resultSet = getPerson.executeQuery();
        resultSet.next();
        int personId = resultSet.getInt(1);
        getPerson.close();

        PreparedStatement getLocation = connection.prepareStatement(GET_LOCATION_BY_PERSON_ID);
        getLocation.setInt(1, personId);
        ResultSet result = getLocation.executeQuery();
        result.next();
        int locationId = result.getInt(1);
        getLocation.close();

        PreparedStatement updateCoordinates = connection.prepareStatement(UPDATE_COORDINATE);
        updateCoordinates.setLong(1, coordinates.getX());
        updateCoordinates.setDouble(2, coordinates.getY());
        updateCoordinates.setInt(3, coordinateId);
        updateCoordinates.executeUpdate();
        updateCoordinates.close();

        PreparedStatement updateLocation = connection.prepareStatement(UPDATE_LOCATION);
        updateLocation.setDouble(1, location.getX());
        updateLocation.setDouble(2, location.getY());
        updateLocation.setLong(3, location.getZ());
        updateLocation.setString(4, location.getName());
        updateLocation.setInt(5, locationId);
        updateLocation.executeUpdate();
        updateLocation.close();

        PreparedStatement updatePerson = connection.prepareStatement(UPDATE_PERSON);
        updatePerson.setString(1, owner.getName());
        updatePerson.setInt(2, owner.getHeight());
        updatePerson.setObject(3, owner.getEyeColor().getName(), Types.OTHER);
        updatePerson.setObject(4, owner.getHairColor().getName(), Types.OTHER);
        updatePerson.setObject(5, owner.getNationality().getName(), Types.OTHER);
        updatePerson.setInt(6, personId);
        updatePerson.executeUpdate();
        updatePerson.close();

        PreparedStatement updateProduct = connection.prepareStatement(UPDATE_PRODUCT);
        updateProduct.setString(1, product.getName());
        updateProduct.setTimestamp(2, Timestamp.from(product.getCreationDate().toInstant()));
        updateProduct.setInt(3, product.getPrice());
        updateProduct.setObject(4, product.getUnitOfMeasure().getName(), Types.OTHER);
        updateProduct.setInt(5, prodId);
        updateProduct.executeUpdate();
        updateProduct.close();
    }

    public synchronized void removeProduct(int id) throws SQLException {
        PreparedStatement getCoordinateId = connection.prepareStatement(GET_COORDINATES_BY_PRODUCTS_ID);
        getCoordinateId.setInt(1, id);
        ResultSet set = getCoordinateId.executeQuery();
        set.next();
        int coordinateId = set.getInt(1);
        getCoordinateId.close();

        PreparedStatement getPerson = connection.prepareCall(GET_PERSON_BY_PRODUCT_ID);
        getPerson.setInt(1, id);
        ResultSet resultSet = getPerson.executeQuery();
        resultSet.next();
        int personId = resultSet.getInt(1);
        getPerson.close();

        PreparedStatement getLocation = connection.prepareStatement(GET_LOCATION_BY_PERSON_ID);
        getLocation.setInt(1, personId);
        ResultSet result = getLocation.executeQuery();
        result.next();
        int locationId = result.getInt(1);
        getLocation.close();

        connection.setAutoCommit(false);
        connection.setSavepoint();

        PreparedStatement remProduct = connection.prepareCall(REMOVE_PRODUCT_BY_ID);
        remProduct.setInt(1, id);
        remProduct.executeUpdate();
        remProduct.close();

        PreparedStatement remPerson = connection.prepareCall(REMOVE_PERSON_BY_ID);
        remPerson.setInt(1, personId);
        remPerson.executeUpdate();
        remPerson.close();

        PreparedStatement remCoordinate = connection.prepareStatement(REMOVE_COORDINATE_BY_ID);
        remCoordinate.setInt(1, coordinateId);
        remCoordinate.executeUpdate();
        remCoordinate.close();

        PreparedStatement remLocation = connection.prepareStatement(REMOVE_LOCATION_BY_ID);
        remLocation.setInt(1, locationId);
        remLocation.executeUpdate();
        remLocation.close();

        connection.commit();
        connection.setAutoCommit(true);
    }

    public boolean checkById(int prod_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CHECK_PRODUCT_BY_ID);
        statement.setInt(1, prod_id);
        ResultSet set = statement.executeQuery();
        set.next();
        boolean isExist = set.getBoolean(1);
        statement.close();
        return isExist;
    }

    public int registerUser(String userName, String password) throws SQLException {
        if (checkRegisterUser(userName)) {
            return -1;
        }
        PreparedStatement statement = connection.prepareStatement(REGISTER_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, userName);
        statement.setString(2, password);
        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        set.next();
        int id = set.getInt(1);
        statement.close();
        return id;
    }

    public int getUserIdByName(String userName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_USER_ID_BY_NAME);
        statement.setString(1, userName);
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            int id = set.getInt(1);
            statement.close();
            return id;
        } else {
            statement.close();
            return -1;
        }
    }

    public int logIn(String userName, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(VALIDATE_USER);
        statement.setString(1, userName);
        statement.setString(2, password);
        ResultSet set = statement.executeQuery();
        set.next();
        int count = set.getInt(1);
        statement.close();

        if (count == 1) {
            PreparedStatement getId = connection.prepareStatement(GET_USER_ID_BY_NAME);
            getId.setString(1, userName);
            ResultSet idSet = getId.executeQuery();
            idSet.next();
            int id = idSet.getInt(1);
            getId.close();
            return id;
        } else {
            return -1;
        }
    }

    public boolean checkRegisterUser(String userName) throws SQLException {
        PreparedStatement state = connection.prepareStatement(CHECK_USER_EXIST);
        state.setString(1, userName);
        ResultSet result = state.executeQuery();
        result.next();
        int count = result.getInt(1);
        state.close();
        return count == 1;
    }

    public List<Integer> getProductsByUserId(int userId) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        PreparedStatement state = connection.prepareStatement(GET_PRODUCTS_BY_USER_ID);
        state.setInt(1, userId);
        ResultSet set = state.executeQuery();
        while (set.next()) {
            ids.add(set.getInt("prod_id"));
        }
        state.close();
        return ids;
    }


    public boolean isOwner(int productId, int userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CHECK_PRODUCT_OWNER);
        statement.setInt(1, productId);
        statement.setInt(2, userId);
        ResultSet set = statement.executeQuery();
        set.next();
        int count = set.getInt(1);
        return count == 1;
    }

    public Connection getConnection() {
        return connection;
    }
}
