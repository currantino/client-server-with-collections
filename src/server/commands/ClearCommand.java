package server.commands;

import server.commands.types.Writable;
import server.data.Data;

import static server.JdbcServer.*;

public class ClearCommand extends Command implements Writable {
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
