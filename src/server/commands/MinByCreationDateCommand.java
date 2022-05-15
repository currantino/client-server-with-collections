package server.commands;

import route.Route;
import server.data.Data;

import java.util.LinkedList;

public class MinByCreationDateCommand extends Command {
    public MinByCreationDateCommand() {
        this.name = "min_by_creation_date";
        this.desc = "get the first element by creation date";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            LinkedList<Route> sortedByDate = new LinkedList<>(Data.getRoutes());
            sortedByDate.sort(new DateTimeComparator() {
                @Override
                public int compare(Route o1, Route o2) {
                    return o1.getCreationDate().compareTo(o2.getCreationDate());
                }
            });
            return sortedByDate.getFirst().toString();
        }
        return "collection is empty";
    }
}