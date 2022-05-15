package server.commands;

import route.Route;
import server.data.Data;

import java.util.Arrays;

public class PrintUniqueDistanceCommand extends Command {
    public PrintUniqueDistanceCommand() {
        super("print_unique_distance", "prints unique distance values of all routes");
    }

    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            return Arrays.toString(Data.getRoutes().stream().mapToDouble(Route::getDistance).distinct().toArray());
        }
        return "collection is empty";
    }
}
