package server.commands;

import server.commands.types.NotCheckable;
import server.commands.types.Readable;
import server.data.Data;

public class HelpCommand extends Command implements NotCheckable, Readable {
    public HelpCommand() {
        super("help", "prints out a list of valid commands with descriptions");
    }

    public String execute() {
        String result = "";
        for (Command command : Data.getCommands().values()) {
            result = result.concat(command.getName() + ": " + command.getDesc() + "\n");
        }
        return result;
    }
}

