//package server;
//
//import java.nio.ByteBuffer;
//
//import static server.jdbcServer.channel;
//
//public class SendResultThread implements Runnable {
//
//    public void run() {
//        ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
//        channel.send(resultBuffer, clientAddress);
//    }
//}
