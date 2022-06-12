package server.commands;

import mid.ServerRequest;
import mid.route.Route;
import server.commands.types.NotCheckable;
import server.commands.types.Writable;
import server.data.Data;

import static server.Server.pdb;

public class RemoveUserCommand extends ArgumentableCommand implements Writable, NotCheckable {

    public RemoveUserCommand() {
        super("suicide", "removes your account and all your routes");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        for (Route route : Data.getRoutes()) {
            if (pdb.checkCreator(route.getId(), login, password)) pdb.removeElementById(route.getId());
        }
        Data.setRoutes(pdb.getElements());
        if (pdb.removeUser(login, password)) return "bye-bye";
        else return "you are already dead";
    }
}
