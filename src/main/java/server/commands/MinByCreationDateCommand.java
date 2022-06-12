package server.commands;

import mid.route.Route;
import server.commands.types.Argumentable;
import server.commands.types.Readable;
import server.data.Data;

import java.util.LinkedList;

public class MinByCreationDateCommand extends Command implements Readable {
    public MinByCreationDateCommand() {
        super("min_by_creation_date", "get the first element by creation date");
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
            return sortedByDate.getFirst().show();
        }
        return "collection is empty";
    }
}