package server;

import server.data.Data;
import server.database.RoutePostgresSqlDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    public static RoutePostgresSqlDatabase pdb;
    public static ExecutorService pool;
    public static DatagramChannel channel;
    public static Logger LOGGER;

    private static String serverName;
    private static int serverPort;
    private static String dbPropertiesPath = "config/db.properties";
    private static String serverPropertiesPath = "config/server.properties";
    private static Properties dbProperties = new Properties();
    private static Properties serverProperties = new Properties();
    private static InetSocketAddress serverAdd;

    public static void main(String[] args) throws IOException, InterruptedException {

        ByteBuffer requestSize;

        LOGGER = Logger.getLogger("multithreading server");
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(serverPropertiesPath)) {
            serverProperties.load(inputStream);
        }
        serverName = serverProperties.getProperty("server.hostname", "localhost");
        serverPort = Integer.parseInt(serverProperties.getProperty("server.port", "1234"));
        serverAdd = new InetSocketAddress(InetAddress.getByName(serverName), serverPort);
        channel = DatagramChannel.open();
        channel.bind(serverAdd);
        LOGGER.info("server is listening at: " + serverAdd);

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(dbPropertiesPath)) {
            dbProperties.load(inputStream);
        }
        String dbUrl = dbProperties.getProperty("url", "jdbc\\:postgresql\\://pg\\:5432/studs");
        pdb = new RoutePostgresSqlDatabase(dbUrl, dbProperties);
        Data.setRoutes(pdb.getElements());
        Data.setCommands();
        LOGGER.info("db connection established\n\n");
        pool = Executors.newCachedThreadPool();
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