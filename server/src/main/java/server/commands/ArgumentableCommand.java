package server.commands;

import common.ServerRequest;
import server.commands.types.Argumentable;

public class ArgumentableCommand extends Command implements Argumentable {
    protected Object argument;

    public ArgumentableCommand(String name, String desc) {
        super(name, desc);
    }

    @Override
    public String execute(ServerRequest argument) {
        return null;
    }

    @Override
    public void setArgument(ServerRequest request) {
        argument = request.getArgument();
    }
}
