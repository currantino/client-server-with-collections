package server.database;

import java.util.List;

public interface Database<T> {
    boolean addElement(T newElement);

    boolean removeElementById(int id);


    boolean registerUser(String email, String password);

    boolean removeUser(String email, String password);

    int getUserId(String email, String password);

    boolean checkExistence(int id);

    boolean updateElement(T elementToUpdate);

    boolean checkLogin(String login);

    boolean checkPassword(String login, String password);

    List<T> getElements();
}
