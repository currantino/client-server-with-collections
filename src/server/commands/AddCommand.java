package server.commands;

import route.Location;
import route.Route;
import server.Server;
import server.data.Data;
import server.jdbcServer;

import java.sql.SQLException;

/**
 * Команда для добавления нового элемента в коллекцию через комндную строку
 */

public class AddCommand extends SQLCommand {

    public AddCommand() {
        super("add", "adds an element to the collection of routes", "INSERT INTO routes VALUES(nextval('routes_id_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?");
    }

    public String execute() {
        try {
            if (jdbcServer.argument != null) {

//            Route newRoute = Data.generateAndSetId((Route) Server.argument);
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
//                jdbcServer.conn.prepareStatement(getStatement()).executeUpdate();
                Data.getRoutes().add(newRoute);
                Data.getCommands().get("sort_by_distance").execute();
                return getStatement();
            }
            else System.out.println("route is null");
        } catch (ClassCastException ex) {
            return "invalid argument";
        }
        return "argument is null";
    }
}
