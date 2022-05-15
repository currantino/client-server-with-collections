package server.commands;

import route.Route;
import server.data.Data;

public class AvgDistanceCommand extends Command {

    public AvgDistanceCommand() {
        super("average_of_distance", "returns average of distance of all routes included in the collection");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            return Double.valueOf(Data.getRoutes().stream().mapToDouble(Route::getDistance).average().getAsDouble()).toString();
        }
        return "collection is empty";
    }
}
