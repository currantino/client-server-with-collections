package server.commands;

import server.jdbcServer;

import static server.jdbcServer.pdb;

public class RegisterCommand extends Command implements NotCheckable {

    public RegisterCommand() {
        super("register", "register a new user");
    }

    @Override
    public String execute() {
        if (pdb.checkLogin(jdbcServer.login)) return "login already in use";
        if (pdb.registerUser(jdbcServer.login, jdbcServer.password)) return "registration successful";
        else return "registration failed";
    }
}
