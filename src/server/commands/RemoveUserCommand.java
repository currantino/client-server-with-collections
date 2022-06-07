package server.commands;

import mid.route.Route;
import server.Writable;
import server.data.Data;

import static server.JdbcServer.*;

public class RemoveUserCommand extends Command implements Writable {

    public RemoveUserCommand() {
        super("suicide", "removes your account and all your routes");
    }

    @Override
    public String execute() {
        for (Route route : Data.getRoutes()) {
            if (pdb.checkCreator(route.getId(), login, password)) pdb.removeElementById(route.getId());
        }
        Data.setRoutes(pdb.getElements());
        if (pdb.removeUser(login, password)) return "bye-bye";
        else return "congratulations! you are immortal";
    }
}
