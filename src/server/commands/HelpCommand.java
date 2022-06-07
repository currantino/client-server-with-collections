package server.commands;

import server.NotCheckable;
import server.data.Data;

public class HelpCommand extends Command implements NotCheckable {
    public HelpCommand() {
        super("help", "prints out a list of valid commands with descriptions");
    }

    public String execute() {
        String result = "";
        for (Command command : Data.getCommands().values()) {
            result = result.concat(command.getName() + ": " + command.getDesc() + "\n");
        }
        System.out.println(result);
        return result;
    }
}

