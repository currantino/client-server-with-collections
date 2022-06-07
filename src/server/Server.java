package server;

import server.database.Database;

public interface Server<T> {
    void start(Database<T> database);
}
