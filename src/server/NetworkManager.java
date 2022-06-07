package server;

import server.data.Data;
import server.database.RoutePostgresSqlDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Properties;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class NetworkManager {
    public static RoutePostgresSqlDatabase pdb;
    private static String dbURL = "jdbc:postgresql://localhost:5432/studs";
    private static String propertiesPath = "/Users/boi/Desktop/client-server-with-collections/config/db.cfg";
    private static Properties serverInfo = new Properties();
    private static Properties info = new Properties();
//    private static InetSocketAddress serverAdd = new InetSocketAddress(serverInfo.getProperty("name"), parseInt(serverInfo.getProperty("port")));
    private static InetSocketAddress serverAdd = new InetSocketAddress("localhost", 1234);

    public static void main(String[] args) throws IOException {
//        serverInfo.load(new FileInputStream("/Users/boi/Desktop/client-server-with-collections/config/server.cfg)"));
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(serverAdd);

        info.load(new FileInputStream(propertiesPath));
        pdb = new RoutePostgresSqlDatabase(dbURL, info);
        Data.setRoutes(pdb.getElements());

        try (Selector selector = Selector.open()) {
            SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey selectionKey : keys) {
                    key = selectionKey;
                    keys.remove(selectionKey);
                    if (key.isValid()) {
                        if (key.isReadable() || key.isWritable())
                        System.out.println("creating new thread");
                        new Thread(new RequestReceiverHandler(channel)).start();
//                        if (key.isReadable()) {
//                            key.channel().register(selector, SelectionKey.OP_WRITE);
//                        }
//                        if (key.isWritable()) {
////                            new Thread(resultSender(result)).start();
//                            key.channel().register(selector, SelectionKey.OP_READ);
                    }
                }
            }
        }
    }
}
