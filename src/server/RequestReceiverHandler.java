//package server;
//
//import mid.ServerRequest;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.Channel;
//import java.nio.channels.DatagramChannel;
//import java.nio.channels.SelectableChannel;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class RequestReceiverHandler implements Runnable {
//
//    private DatagramChannel channel;
//    private ExecutorService pool = Executors.newCachedThreadPool();
//
//    public RequestReceiverHandler(DatagramChannel channel) {
//        this.channel = channel;
//    }
//
//    @Override
//    public void run() {
//        try {
//            ByteBuffer requestBuffer = ByteBuffer.allocate(4096);
//
//            //Получение датаграммы в байтбуффер и сохраняем адрес клиента в remoteAdd
//            SocketAddress clientAddress = channel.receive(requestBuffer);
//            byte[] arr = requestBuffer.array();
//
//            //Создаем поток ввода для считывания запроса
//            ByteArrayInputStream bais = new ByteArrayInputStream(arr);
//            ObjectInputStream ois = new ObjectInputStream(bais);
//
//            ServerRequest request = (ServerRequest) ois.readObject();
//            String result = pool.submit(new RequestProcessorHandler(request)).get();
//            ByteBuffer resultBuffer = ByteBuffer.wrap(result.getBytes());
//            channel.send(resultBuffer, clientAddress);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}