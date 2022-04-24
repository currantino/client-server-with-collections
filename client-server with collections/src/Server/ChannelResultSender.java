package Server;//package Server.Server;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//
//public class ChannelResultSender {
//
//    private DatagramChannel channel;
//    ByteBuffer buffer;
//    public void sendResult() {
//        while (true) {
//            try {
//                SocketAddress clientAddress = ChannelCommandReader.remoteAdd;
//                System.out.println(clientAddress);
//                channel = DatagramChannel.open();
////                channel.bind(clientAddress);
//                System.out.println("preparing to send the result");
//                String result = "here is the result for your request";
//                buffer = ByteBuffer.wrap(result.getBytes());
//
//                channel.send(buffer, clientAddress);
//                buffer.flip();
//                buffer.clear();
//                System.out.println("sent to client: " + result);
//                channel.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//
//    }
//}
