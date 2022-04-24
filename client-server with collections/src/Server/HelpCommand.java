package Server;

public class HelpCommand extends Command {
    public HelpCommand() {
        this.name = "help";
        this.desc = "prints out a list of valid commands with descriptions";
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

