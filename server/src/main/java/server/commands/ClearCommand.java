package server.commands;

import common.ServerRequest;
import server.commands.types.Writable;
import server.data.Data;

import static server.Server.LOGGER;
import static server.Server.pdb;


public class ClearCommand extends ArgumentableCommand implements Writable {
    public ClearCommand() {
        super("clear", "clear the collection");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        int executorId = pdb.getUserId(login, password);
        LOGGER.info("user " + executorId + " trying to remove all his elements");
        if (pdb.removeAllElements(executorId)) {
            Data.setRoutes(pdb.getElements());
            return "collection has been was cleared";
        }
        return "you can not delete routes created by other users";
    }
}
