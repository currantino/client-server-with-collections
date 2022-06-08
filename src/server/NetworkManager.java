package server;

import server.data.Data;
import server.database.RoutePostgresSqlDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
//        try (Selector selector = Selector.open()) {
//            SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
//            while (true) {
//                selector.select();
//                Set<SelectionKey> keys = selector.selectedKeys();
//                for (SelectionKey selectionKey : keys) {
//                    key = selectionKey;
//                    keys.remove(selectionKey);
//                    if (key.isValid()) {
//                        if (key.isReadable()) {
//
//                            key.channel().register(selector, SelectionKey.OP_WRITE);
//                        }
//                        if (key.isWritable()) {
//                            key.channel().register(selector, SelectionKey.OP_READ);
//                        }
//                    }
//                }
//            }
//        }

//        try {
//            ExecutorService pool = Executors.newCachedThreadPool();
//            while (true) {
//                Callable<ServerRequest> task = getTask(channel);
//                FutureTask<ServerRequest> futureTask = new FutureTask<>(task);
//                new Thread(futureTask).start();
//                ServerRequest request = futureTask.get();
//                Future<String> futureResult = pool.submit(new RequestProcessor(request));
//                String result = futureResult.get();
//                byte[] resultArr = result.getBytes(StandardCharsets.UTF_8);
//                SocketAddress clientAddress = request.getSenderAddress();
//                new Thread(new ResultSender(resultArr, clientAddress, channel)).start();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static Callable<ServerRequest> getTask(DatagramChannel channel) {
//        return () -> {
//            try {
//                ByteBuffer requestBuffer = ByteBuffer.allocate(4096);
//
//                //Получение датаграммы в байтбуффер и сохранение адрес клиента в clientAddress
//                SocketAddress clientAddress = channel.receive(requestBuffer);
//                byte[] arr = requestBuffer.array();
//
//                //Создаем поток ввода для считывания запроса
//                ByteArrayInputStream bais = new ByteArrayInputStream(arr);
//                ObjectInputStream ois = new ObjectInputStream(bais);
//                ServerRequest request = (ServerRequest) ois.readObject();
//                request.setSenderAddress(clientAddress);
//                return request;
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                return null;
//            }
//        };
//    }
//}