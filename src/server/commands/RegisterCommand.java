package server.commands;

import mid.ServerRequest;
import server.commands.types.NotCheckable;

import static server.NetworkManager.LOGGER;
import static server.NetworkManager.pdb;


public class RegisterCommand extends Command implements NotCheckable {

    public RegisterCommand() {
        super("register", "register a new user");
    }

    public String execute(ServerRequest request) {
        System.out.println("NIGGA");
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
