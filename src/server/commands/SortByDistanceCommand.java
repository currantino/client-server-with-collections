package server.commands;

import server.data.Data;

public class SortByDistanceCommand extends Command {

    public SortByDistanceCommand() {
        this.name = "sort";
        this.desc = "sorts the collection by distance";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            Data.getRoutes().sort(new DistanceComparator());
        }
        return "collection is empty";
    }
}
