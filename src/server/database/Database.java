package server.database;

import mid.route.Route;

public interface Database {
    boolean add(Route route);

    boolean registerUser(String email, String password);

    int getUserId(String email, String password);
}
