package server;

import server.data.Data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

public class Server {


    public static void main(String[] args) {
        Logger logger = Logger.getLogger("server logger");
        Data.setCommands();
        while (true) {
            try {
                DatagramChannel channel;
                SocketAddress remoteAdd;
                String result;

                //Открываем канал, который слушает на локалхосте в порте 1234
                InetSocketAddress iAdd = new InetSocketAddress("localhost", 1234);
                channel = DatagramChannel.open();
                channel.bind(iAdd);
                logger.fine("server started at: " + iAdd);
                //System.out.println("Channel Server.Server started: " + iAdd);

                //Создаем байтбуфер для приема запроса от клиента
                ByteBuffer buffer = ByteBuffer.allocate(1024);

                //Ждем получения буфера и сохраняем адрес клиента в remoteAdd
                remoteAdd = channel.receive(buffer);
                byte[] arr = buffer.array();

                //Создаем поток ввода для считывания запроса
                ByteArrayInputStream bais = new ByteArrayInputStream(arr);
                ObjectInputStream ois = new ObjectInputStream(bais);

                String request = (String) ois.readObject();
                System.out.println(request + " received from client at: " + remoteAdd);

                if (Data.getCommands().containsKey(request)) {
                    result = Data.getCommands().get(request).execute();
                } else {
                    result = "unknown command, use 'help' ";
                }
                buffer.flip();
                buffer.clear();
                buffer = ByteBuffer.wrap(result.getBytes());
                channel.send(buffer, remoteAdd);
                System.out.println("'" + result + "'" + " sent to client at: " + remoteAdd);
                channel.close();

            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

