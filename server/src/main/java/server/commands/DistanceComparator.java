package server.commands;

import common.route.Route;

import java.util.Comparator;

public class DistanceComparator implements Comparator<Route> {
    public DistanceComparator() {
    }

    public int compare(Route o1, Route o2) {
        return (int) (o1.getDistance() - o2.getDistance());
    }
}
