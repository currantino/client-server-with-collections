package server.commands;

import route.Route;

import java.util.Comparator;

public class DateTimeComparator implements Comparator<Route> {
    @Override
    public int compare(Route o1, Route o2) {
        return -o1.getCreationDate().compareTo(o2.getCreationDate());
    }
}
