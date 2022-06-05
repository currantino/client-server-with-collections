package server.database;

import mid.route.Route;
import server.jdbcServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Properties;

public class PostgresSqlDatabase implements Database {
    private final String SALT = "pepper";
    private String dbURL;
    private Properties info = new Properties();

    public PostgresSqlDatabase(String dbURL) {
        this.dbURL = dbURL;
        try {
            info.load(new FileInputStream("/Users/boi/Desktop/client-server-with-collections/config/db.cfg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(dbURL, info)) {

            //users table
            PreparedStatement usersStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS routes_users(" +
                            "user_id BIGSERIAL PRIMARY KEY, " +
                            "login VARCHAR NOT NULL, " +
                            "password VARCHAR NOT NULL)");
            usersStatement.executeUpdate();
            usersStatement.close();

            //routes table
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
                            "user_id BIGINT NOT NULL REFERENCES routes_users)"
            );
            routesStatement.executeUpdate();
            routesStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean add(Route newRoute) {
        boolean result = false;
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO routes " +
                    "(name, distance, from_name, from_x, from_y, from_z, to_name, to_x, to_y, to_z, user_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, newRoute.getName());
            statement.setFloat(2, newRoute.getDistance());
            statement.setString(3, newRoute.getFrom().getName());
            statement.setInt(4, newRoute.getFrom().getX());
            statement.setInt(5, newRoute.getFrom().getY());
            statement.setInt(6, newRoute.getFrom().getZ());
            statement.setString(7, newRoute.getTo().getName());
            statement.setInt(8, newRoute.getTo().getX());
            statement.setInt(9, newRoute.getTo().getY());
            statement.setInt(10, newRoute.getTo().getZ());
            statement.setInt(11, getUserId(jdbcServer.login, jdbcServer.password));
            if (statement.executeUpdate() > 0) result = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("db connection failed");
        } finally {
            return result;
        }
    }

    @Override
    public boolean registerUser(String email, String password) {
        try (Connection connection = DriverManager.getConnection(dbURL, info)) {
            String hashedPassword = getHash(password);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO routes_users(login, password) " +
                            "VALUES(?, ?)");
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
            statement.close();
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
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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