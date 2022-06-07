//package server;
//
//import mid.ServerRequest;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.SocketAddress;
//import java.nio.ByteBuffer;
//
//import static server.jdbcServer.channel;
//
//public class GetRequestThread implements Runnable {
//    private SocketAddress clientAddress;
//    private ServerRequest request;
//    private String command;
//    private Object argument;
//    private String login;
//    private String password;
//
//    private void readRequestData(ServerRequest request) {
//        command = request.getCommand();
//        argument = request.getArgument();
//        login = request.getLogin();
//        password = request.getPassword();
//    }
//
//    @Override
//    public void run() {
//        ByteBuffer requestBuffer = ByteBuffer.allocate(4096);
//
//        //Получение датаграммы в байтбуффер и сохраняем адрес клиента в remoteAdd
//        try {
//            clientAddress = channel.receive(requestBuffer);
//            ThreadLocal<byte[]> arr = new ThreadLocal<>()
//            arr.set(requestBuffer.array());
//
//            //Создаем поток ввода для считывания запроса
//            ThreadLocal<ByteArrayInputStream> bais = new ThreadLocal<>();
//            bais.set(new ByteArrayInputStream(arr.get()));
//            ThreadLocal<ObjectInputStream> ois = new ThreadLocal<>();
//            ois.set(new ObjectInputStream(bais.get()));
//
//            request = (ServerRequest) ois.get().readObject();
//            readRequestData(request);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getCommand() {
//        return command;
//    }
//
//    public Object getArgument() {
//        return argument;
//    }
//
//    public String getLogin() {
//        return login;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//}
