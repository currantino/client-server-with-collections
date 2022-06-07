//package server;
//
//import mid.route.Route;
//import server.database.Database;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.nio.channels.DatagramChannel;
//import java.util.HashSet;
//import java.util.Set;
//
//public class MultiThreadServer implements Server<Route> {
//    private String serverName = "localhost";
//    private int serverPort = 1234;
//    private InetSocketAddress serverAdd = new InetSocketAddress(serverName, serverPort);
//    private Database database;
//    private Set<SocketAddress> clientSet = new HashSet<>();
//
//    @Override
//    public void start(Database<Route> database) {
//        try (DatagramChannel channel = DatagramChannel.open()) {
//            this.database = database;
//            channel.bind(serverAdd);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void§
//}
