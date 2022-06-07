package server.commands;

import server.JdbcServer;
import server.commands.types.Writable;
import server.data.Data;

import static server.JdbcServer.*;

public class RemoveByIdCommand extends Command implements Writable {

    public RemoveByIdCommand() {
        super("remove_by_id", "removes the element with the given id");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            try {
                int id = Integer.parseInt((String) JdbcServer.argument);
                if (pdb.checkExistence(id)) {
                    if (pdb.checkCreator(id, login, password)) {
                        if (pdb.removeElementById(id)) {
                            Data.setRoutes(pdb.getElements());
                            return "route with id = " + id + " has been removed";
                        }
                    } else return "you can not remove elements created by other users";
                } else return "route with id = " + id + " does not exist";
            } catch (NumberFormatException e) {
                return "invalid id";
            }
        }
        return "collection is empty";
    }
}