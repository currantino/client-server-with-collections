package server.commands;

import server.data.Data;

import static server.jdbcServer.*;

public class ClearCommand extends Command {
    public ClearCommand() {
        super("clear", "clear the collection");
    }

    public String execute() {
        int executorId = pdb.getUserId(login, password);
        if (pdb.removeAllElements(executorId)) {
            Data.setRoutes(pdb.getElements());
            return "collection has been was cleared";
        }
        return "you can not delete routes created by other users";
    }
}
