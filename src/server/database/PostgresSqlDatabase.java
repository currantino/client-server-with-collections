package server.database;

import route.Route;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresSqlDatabase implements Database {

    String dbURL = "jdbc:postgresql://localhost:5432/studs";
    Properties info = new Properties();

    public PostgresSqlDatabase() {
        try {
            info.load(new FileInputStream("/Users/boi/Desktop/client-server-with-collections/config/db.cfg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean add(Route newRoute) {
        boolean result = false;
        try {
            Connection connection = DriverManager.getConnection(dbURL, info);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO routes " +
                    "(name, from_name, from_x, from_y, from_z, to_name, to_x, to_y, to_z)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, newRoute.getName());
            statement.setString(2, newRoute.getFrom().getName());
            statement.setInt(3, newRoute.getFrom().getX());
            statement.setInt(4, newRoute.getFrom().getY());
            statement.setInt(5, newRoute.getFrom().getZ());
            statement.setString(6, newRoute.getTo().getName());
            statement.setInt(7, newRoute.getTo().getX());
            statement.setInt(8, newRoute.getTo().getY());
            statement.setInt(9, newRoute.getTo().getZ());
            if (statement.executeUpdate() > 0) result = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("db connection failed");
        } finally {
            return result;
        }
    }
}
