package server.commands;

import mid.route.Route;
import server.Readable;
import server.data.Data;

public class AvgDistanceCommand extends Command implements Readable {

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
