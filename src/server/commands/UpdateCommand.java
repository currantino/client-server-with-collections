//package server.commands;
//
//import mid.route.Location;
//import mid.route.Route;
//import server.data.Data;
//import server.jdbcServer;
//
//import java.sql.SQLException;
//
//public class UpdateCommand extends Command {
//
//    boolean found = false;
//
//    public UpdateCommand() {
//        super("update", "updates the mid.route with required id");
//    }
//
//    @Override
//    public String execute() {
//        try {
//            if (!Data.getRoutes().isEmpty()) {
//                if (jdbcServer.argument != null) {
//                    Route routeToUpdate = (Route) jdbcServer.argument;
//                    int idForUpdating = routeToUpdate.getId();
//                    Location newFrom = routeToUpdate.getFrom();
//                    Location newTo = routeToUpdate.getTo();
//                    setStatement("UPDATE routes " +
//                            "SET " +
//                            "name = '" + routeToUpdate.getName() + "', " +
//                            "from_name = '" + newFrom.getName() + "', " +
//                                "from_x = " + newFrom.getX() + ", " +
//                                "from_y = " + newFrom.getY() + ", " +
//                                "from_z = " + newFrom.getZ() + ", " +
//                            "to_name = '" + newTo.getName() + "', " +
//                                "to_x = " + newTo.getX() + ", " +
//                                "to_y = " + newTo.getY() + ", " +
//                                "to_z = " + newTo.getZ() +
//                            "WHERE id = " + idForUpdating + ';');
//                    if (jdbcServer.conn.prepareStatement(getStatement()).executeUpdate() > 0) {
//                        Data.getRoutes().add(Data.generateAndSetId(routeToUpdate));
//                        Data.getCommands().get("sort_by_distance").execute();
//                        if (Data.getRoutes().removeIf(route -> route.getId() == idForUpdating)) {
//                            Data.getRoutes().add(routeToUpdate);
//                            return "mid.route with id = " + idForUpdating + " updated";
//                        } else return "mid.route with required id not found";
//                    } else return "mid.route is not null, but it wasn't added to db";
//                } else return "given mid.route is null";
//
//            } else return "collection is empty";
//        } catch (ClassCastException | SQLException cce) {
//            return "invalid argument";
//        }
//    }
//}
