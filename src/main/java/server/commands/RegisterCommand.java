package server.commands;

import mid.ServerRequest;
import server.commands.types.NotCheckable;
import server.commands.types.Writable;

import static server.Server.LOGGER;
import static server.Server.pdb;


public class RegisterCommand extends ArgumentableCommand implements NotCheckable, Writable {

    public RegisterCommand() {
        super("register", "register a new user");
    }

    public String execute(ServerRequest request) {
        unpackRequest(request);
        LOGGER.info("registering user " + login + " " + password);
        if (pdb.checkLogin(login)) {
            LOGGER.info("login " + login + "already in use");
            result = "login already in use";
        } else if (pdb.registerUser(login, password)) {
            LOGGER.info("registration successful");
            result = "registration successful";
        } else result = "registration failed";
        return result;
    }
}
