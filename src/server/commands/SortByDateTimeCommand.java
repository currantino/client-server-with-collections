package server.commands;

import server.data.Data;

import java.util.Collections;

public class SortByDateTimeCommand extends Command {

    public SortByDateTimeCommand() {
        name = "sort_by_creation_date";
        desc = "sort routes by creation date";
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            Collections.sort(Data.routes, new DateTimeComparator());
            return "collection was sorted by creation date";
        }
        return "colliction is empty";
    }
}
