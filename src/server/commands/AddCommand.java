package server.commands;

import route.Location;
import route.Route;
import server.data.Data;
import server.jdbcServer;

import java.sql.SQLException;

/**
 * Команда для добавления нового элемента в коллекцию через командную строку
 */

public class AddCommand extends SqlCommand {

    public AddCommand() {
        super("add", "adds an element to the collection of routes", "INSERT INTO routes VALUES(nextval('routes_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?");
    }

    public String execute() {
        try {
            if (jdbcServer.argument != null) {

                Route newRoute = (Route) jdbcServer.argument;
                Location newFrom = newRoute.getFrom();
                Location newTo = newRoute.getTo();
                setStatement("INSERT INTO routes VALUES(nextval('routes_id_seq')," +
                        newRoute.getName() + ", " +
                        newFrom.getName() + ", " +
                        newFrom.getX() + ", " +
                        newFrom.getY() + ", " +
                        newFrom.getZ() + ", " +
                        newTo.getName() + ", " +
                        newTo.getX() + ", " +
                        newTo.getY() + ", " +
                        newTo.getZ() +
                        ")");
                if (jdbcServer.conn.prepareStatement(getStatement()).executeUpdate() > 0) {
                    Data.getRoutes().add(Data.generateAndSetId(newRoute));
                    Data.getCommands().get("sort_by_distance").execute();
                    return "new route added to the collection";
                }
                return "route is not null, but it wasn't added to db";
            } else return "route is null";
        } catch (ClassCastException ex) {
            return "invalid argument";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
