//package server;
//
//import server.commands.Command;
//import server.commands.NotCheckable;
//import server.data.Data;
//
//public class ProcessRequestThread implements Runnable{
//
//    @Override
//    public void run() {
//        if (Data.getCommands().containsKey(command)) {
//            Command commandToExecute = Data.getCommands().get(command);
//            if (commandToExecute instanceof NotCheckable) {
//                result = processCommand(commandToExecute);
//            } else if (pdb.checkLogin(login)) {
//                if (pdb.checkPassword(login, password)) {
//                    result = processCommand(Data.getCommands().get(command));
//                } else {
//                    result = "wrong password";
//                }
//            } else {
//                result = "unknown user: use 'register'";
//            }
//        } else {
//            result = "unknown command, use 'help'";
//        }
//    }
//}
