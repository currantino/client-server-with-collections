package server.commands;

import common.ServerRequest;
import server.commands.types.NotCheckable;
import server.commands.types.Readable;

import static server.Server.pdb;

public class LoginCommand extends ArgumentableCommand implements Readable, NotCheckable {

    public LoginCommand() {
        super("login", "login with your user name and password");
    }

    @Override
    public String execute(ServerRequest request) {
        unpackRequest(request);
        if (!pdb.checkLogin(login)) {
            return "unknown user";
        }
        if (!pdb.checkPassword(login, password)) {
            return "incorrect password";
        }
        return "you are welcome!";
    }
}
