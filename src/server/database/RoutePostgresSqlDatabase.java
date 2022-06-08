package server.database;

import mid.route.Route;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class RoutePostgresSqlDatabase implements Database<Route> {
    private final String SALT = "pepper";
    private String dbURL;
    private Properties info;

    public RoutePostgresSqlDatabase(String dbURL, Properties info) {
        this.dbURL = dbURL;
        this.info = info;
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {

            PreparedStatement usersStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS routes_users(" +
                            "user_id BIGSERIAL PRIMARY KEY, " +
                            "login VARCHAR UNIQUE NOT NULL, " +
                            "password VARCHAR NOT NULL)");
            usersStatement.executeUpdate();
            usersStatement.close();

            PreparedStatement routesStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS routes(" +
                            "id BIGSERIAL PRIMARY KEY, " +
                            "name VARCHAR NOT NULL, " +
                            "distance FLOAT NOT NULL CHECK (distance > 0 ), " +
                            "from_name VARCHAR NOT NULL, " +
                            "from_x BIGINT, " +
                            "from_y BIGINT, " +
                            "from_z BIGINT, " +
                            "to_name VARCHAR NOT NULL, " +
                            "to_x BIGINT, " +
                            "to_y BIGINT, " +
                            "to_z BIGINT, " +
                            "creation_datetime TIMESTAMP NOT NULL, " +
                            "user_id BIGINT NOT NULL REFERENCES routes_users)"
            );
            routesStatement.executeUpdate();
            routesStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int addElement(Route newRoute, String login, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO routes " +
                    "(name, distance, from_name, from_x, from_y, from_z, to_name, to_x, to_y, to_z, creation_datetime, user_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING id")) {
                setParams(newRoute, statement);
                statement.setObject(11, newRoute.getCreationDate());
                statement.setInt(12, getUserId(login, password));
                try (ResultSet resultSet = statement.executeQuery();) {
                    while (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    @Override
    public boolean registerUser(String login, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            String hashedPassword = getHash(password);
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO routes_users(login, password) " +
                            "VALUES(?, ?)")) {
                statement.setString(1, login);
                statement.setString(2, hashedPassword);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getUserId(String email, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id FROM routes_users " +
                            "WHERE login" +
                            " = ?" +
                            " AND " +
                            "password = ?");
            statement.setString(1, email);
            statement.setString(2, getHash(password));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean removeUser(String login, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE  FROM routes_users " +
                            "WHERE login = ? " +
                            "AND " +
                            "password = ?")) {
                statement.setString(1, login);
                statement.setString(2, getHash(password));
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkLogin(String login) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM routes_users " +
                    "WHERE login = ?")) {
                statement.setString(1, login);
                return statement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkCreator(int routeId, String email, String password) {
        return getCreatorId(routeId) == getUserId(email, password);
    }

    public int getCreatorId(int routeId) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT user_id FROM routes " +
                            "WHERE id = ?")) {
                statement.setInt(1, routeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public boolean checkExistence(int id) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT count(id) FROM routes " +
                    "WHERE id = ?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) return resultSet.getInt("count") == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateElement(Route routeToUpdate) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE routes " +
                            "SET (name, distance, from_name, from_x, from_y, from_z, to_name, to_x, to_y, to_z) " +
                            " = " +
                            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                            "WHERE id = ?")) {
                setParams(routeToUpdate, statement);
                statement.setInt(11, routeToUpdate.getId());
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setParams(Route routeToUpdate, PreparedStatement statement) throws SQLException {
        statement.setString(1, routeToUpdate.getName());
        statement.setFloat(2, routeToUpdate.getDistance());
        statement.setString(3, routeToUpdate.getFrom().getName());
        statement.setInt(4, routeToUpdate.getFrom().getX());
        statement.setInt(5, routeToUpdate.getFrom().getY());
        statement.setInt(6, routeToUpdate.getFrom().getZ());
        statement.setString(7, routeToUpdate.getTo().getName());
        statement.setInt(8, routeToUpdate.getTo().getX());
        statement.setInt(9, routeToUpdate.getTo().getY());
        statement.setInt(10, routeToUpdate.getTo().getZ());
    }

    @Override
    public boolean checkPassword(String login, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM routes_users " +
                    "WHERE login = ? and password = ?")) {
                statement.setString(1, login);
                statement.setString(2, getHash(password));
                return statement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //вызывать при подключении нового клиента
    @Override
    public List<Route> getElements() {
        List<Route> data = new LinkedList<>();
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM routes"
            )) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        Float distance = resultSet.getFloat("distance");
                        String fromName = resultSet.getString("from_name");
                        int fromX = resultSet.getInt("from_x");
                        int fromY = resultSet.getInt("from_y");
                        int fromZ = resultSet.getInt("from_z");
                        String toName = resultSet.getString("to_name");
                        int toX = resultSet.getInt("to_x");
                        int toY = resultSet.getInt("to_y");
                        int toZ = resultSet.getInt("to_z");
                        LocalDateTime creationDateTime = resultSet.getObject("creation_datetime", LocalDateTime.class);
                        data.add(new Route(id, name, distance, fromName, fromX, fromY, fromZ, toName, toX, toY, toZ, creationDateTime));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public boolean removeElementById(int id) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM routes " +
                            "WHERE id = ?")
            ) {
                statement.setInt(1, id);
                statement.executeUpdate();
                return statement.executeUpdate() == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean removeAllElements(int executorId) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM routes " +
                            "WHERE user_id = ?")) {
                statement.setInt(1, executorId);
                return statement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getHash(String sequence) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(SALT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(sequence.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}