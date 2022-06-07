package server.commands;

import server.Writable;
import server.data.Data;

public class SortByDateTimeCommand extends Command implements Writable {

    public SortByDateTimeCommand() {
        super("sort_by_creation_date", "sort routes by creation date");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            Data.getRoutes().sort(new DateTimeComparator());
            return "collection has been sorted by creation date";
        }
        return "collection is empty";
    }
}
