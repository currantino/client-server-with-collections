package server.commands;

import mid.ServerRequest;
import server.commands.types.Argumentable;
import server.commands.types.NotCheckable;

import static server.JdbcServer.pdb;

public class RegisterCommand extends Command implements NotCheckable, Argumentable<ServerRequest> {

    public RegisterCommand() {
        super("register", "register a new user");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        if (pdb.checkLogin(login)) return "login already in use";
        if (pdb.registerUser(login, password)) return "registration successful";
        else return "registration failed";
    }
}
