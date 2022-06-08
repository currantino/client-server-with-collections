package server;

import server.data.Data;
import server.database.RoutePostgresSqlDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class NetworkManager {
    public static RoutePostgresSqlDatabase pdb;
    public static ExecutorService pool;
    public static DatagramChannel channel;
    public static Logger LOGGER;
    private static String dbURL = "jdbc:postgresql://localhost:5432/studs";
    private static String propertiesPath = "/Users/boi/Desktop/client-server-with-collections/config/db.cfg";
    private static Properties serverInfo = new Properties();
    private static Properties info = new Properties();
    private static InetSocketAddress serverAdd = new InetSocketAddress("localhost", 1234);

    public static void main(String[] args) throws IOException, InterruptedException {

        ByteBuffer requestSize;

        LOGGER = Logger.getLogger("multithreading server");

        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(serverAdd);
        pool = Executors.newCachedThreadPool();


        info.load(new FileInputStream(propertiesPath));
        pdb = new RoutePostgresSqlDatabase(dbURL, info);
        Data.setRoutes(pdb.getElements());
        Data.setCommands();
        LOGGER.fine("db connection established");

        channel.configureBlocking(true);
        while (true) {
            synchronized (channel) {
                requestSize = ByteBuffer.allocate(4);
                channel.receive(requestSize);
                LOGGER.info("main thread received " + requestSize.flip().getInt());
                new Thread(new RequestReceiver(requestSize)).start();
                channel.wait();
            }
        }
    }
}