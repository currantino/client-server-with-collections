package server.commands;

import server.data.Data;

public class SortByDistanceCommand extends Command {

    public SortByDistanceCommand() {
        super("sort_by_distance", "sorts the collection by distance");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            Data.getRoutes().sort(new DistanceComparator());
            return "collection has been sorted by distance";
        }
        return "collection is empty";
    }
}
