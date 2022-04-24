//package Client;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//
//public class PacketResultReader {
//
//    private DatagramSocket ds;
//    private byte[] buffer = new byte[1024];
//
//    public PacketResultReader() {
//    }
//
//    public void readResult() {
//        while (true) {
//            try {
//                ds = new DatagramSocket();
//                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
//                ds.receive(dp);
//                String result = new String(dp.getData(), 0, dp.getLength());
//                System.out.println("result from server: " + result);
//                if (result.equals("exit")) {
//                    ds.close();
//                    System.exit(0);
//                }
//                ds.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
