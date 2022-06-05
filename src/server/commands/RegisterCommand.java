package server.commands;

import server.jdbcServer;

import static server.data.Data.pdb;

public class RegisterCommand extends Command{

    public RegisterCommand() {
        super("register", "register a new user");
    }

    @Override
    public String execute() {
        if (pdb.registerUser(jdbcServer.login, jdbcServer.password)){
            return "registration successful";
        }
        return "registration failed";
    }
}
