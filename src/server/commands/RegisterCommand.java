package server.commands;

import server.JdbcServer;
import server.commands.types.NotCheckable;

import static server.JdbcServer.pdb;

public class RegisterCommand extends Command implements NotCheckable {

    public RegisterCommand() {
        super("register", "register a new user");
    }

    @Override
    public String execute() {
        if (pdb.checkLogin(JdbcServer.login)) return "login already in use";
        if (pdb.registerUser(JdbcServer.login, JdbcServer.password)) return "registration successful";
        else return "registration failed";
    }
}
