package server.commands;

import route.Route;
import server.data.Data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class PrintUniqueDistanceCommand extends Command {
    public PrintUniqueDistanceCommand() {
        this.name = "print_unique_distance";
        this.desc = "prints unique distance values of all routes";
    }

    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            return Arrays.toString(Data.getRoutes().stream().mapToDouble(Route::getDistance).distinct().toArray());
        }
        return "collection is empty";
    }
}
