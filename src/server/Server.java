//package server;
//
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class Server {
//    private static final int DEFAULT_BUFFER_SIZE = 65536;
//    private DatagramChannel channel;
//    private byte[] buffer;
//
//    public Server() {
//        buffer = new byte[DEFAULT_BUFFER_SIZE];
//    }
//
//    public void run() {
//        try {
//            Callable<SocketAddress> task = getTask();
//            ExecutorService pool = Executors.newCachedThreadPool();
//            while (true){
//                Future<SocketAddress> result = pool.submit(task);
//                SocketAddress clientAddress = result.get();
//                byte[] copyData = buffer.clone();
//                new HandlerThread().start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Callable<SocketAddress> getTask() {
//        return () -> {
//            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
//            SocketAddress socketAddress;
//            do {
//                socketAddress = channel.receive(byteBuffer);
//            } while (socketAddress == null);
//            return socketAddress;
//        };
//    }
//}
