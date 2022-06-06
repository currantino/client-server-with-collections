package server.database;

import java.util.List;

public interface Database<T> {
    boolean addElement(T newElement);

    boolean registerUser(String email, String password);

    boolean removeElementById(int id);

    int getUserId(String email, String password);

    boolean checkExistence(int id);

    boolean updateElement(T elementToUpdate);

    boolean checkLogin(String login);

    boolean checkPassword(String login, String password);

    List getData();
}
