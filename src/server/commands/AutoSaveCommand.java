package server.commands;

import org.json.JSONArray;
import server.data.Data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class AutoSaveCommand extends Command {

    public AutoSaveCommand() {
        super("save", "saves the collection in a .json file");
    }

    @Override
    public String execute() {
        if (!Data.getRoutes().isEmpty()) {
            JSONArray jsonArray = new JSONArray(Data.getRoutes());
            try {
                PrintWriter printWriter = new PrintWriter("/Users/boi/Desktop/client-server-with-collections/resources/autoSave.json");
                printWriter.write(jsonArray.toString());
                printWriter.close();
                Data.isSaved = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //            try {
            //                PrintWriter printWriter = new PrintWriter("/Users/A1/IdeaProjects/Collections/src/test/save.json");
            //                for (Route route : Data.getRoutes()) {
            //                    JSONObject element = new JSONObject();
            //                    JSONObject coordinates = new JSONObject();
            //                    coordinates.put("x coordinate", route.getCoordinates().getX());
            //                    coordinates.put("y coordinate", route.getCoordinates().getY());
            //                    JSONObject from = new JSONObject();
            //                    from.put("name", route.getFrom().getName());
            //                    from.put("x", route.getFrom().getX());
            //                    from.put("y", route.getFrom().getY());
            //                    from.put("z", route.getFrom().getZ());
            //                    JSONObject to = new JSONObject();
            //                    to.put("name", route.getTo().getName());
            //                    to.put("x", route.getTo().getX());
            //                    to.put("y", route.getTo().getY());
            //                    to.put("z", route.getTo().getZ());
            //                    element.put("name", route.getName());
            //                    element.put("distance", route.getDistance());
            //                    element.put("coordinates", coordinates);
            //                    element.put("from", from);
            //                    element.put("to", to);
            //                    element.put("creation date", route.getCreationDate().toString());
            //                    jsonArray.put(element);
            //                }
            //                printWriter.write(jsonArray.toString());
            //                printWriter.close();
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            }
            System.out.println("JSON file created: " + jsonArray);
        }
        return null;
    }
}