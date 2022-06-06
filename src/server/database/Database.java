package server.database;

import mid.route.Route;

public interface Database {
    boolean addElement(Route route);

    boolean registerUser(String email, String password);

    int getUserId(String email, String password);
    boolean updateElement(Route routeToUpdate);
    boolean checkLogin(String login);
    boolean checkPassword(String login, String password);
}
