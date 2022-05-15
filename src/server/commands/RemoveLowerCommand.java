package server.commands;

import server.Server;
import server.data.Data;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand() {
        super("remove_lower", "removes routes with distance lower than required");
    }

    @Override
    public String execute() {
        try{
            double dist = Double.parseDouble((String) Server.argument);
            if (!Data.getRoutes().isEmpty()){
                Data.setRoutes(Data.getRoutes().stream().filter(route -> route.getDistance() >= dist).collect(Collectors.toCollection(LinkedList::new)));
                return "routes with distance lower than " + dist + " removed";
            }
            return "collections is empty";
        }catch(NumberFormatException e){
            return "invalid argument";
        }
    }
}
