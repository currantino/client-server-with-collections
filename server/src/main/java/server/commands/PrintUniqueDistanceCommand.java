package server.commands;

import common.route.Route;
import server.commands.types.Readable;
import server.data.Data;

import java.util.Arrays;

public class PrintUniqueDistanceCommand extends Command implements Readable {
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
