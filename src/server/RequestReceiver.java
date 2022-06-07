package server;

import mid.ServerRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import static server.NetworkManager.LOGGER;

public class RequestReceiver implements Runnable {
    private SocketAddress address;
    private ByteBuffer buffer;

    public RequestReceiver(SocketAddress address, ByteBuffer buffer) {
        this.address = address;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        LOGGER.info(this.getClass().getSimpleName() + " thread started");
        try {
            byte[] arr = buffer.array();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(arr)) {
                try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                    ServerRequest request = (ServerRequest) ois.readObject();//TODO Сначала получать датаграмму с длиной датаграммы запроса и создавать подходящий буфер
                    request.setSenderAddress(address);
                    LOGGER.info(request + " received");
                    NetworkManager.pool.submit(new RequestProcessor(request));
                    request = null;
                    address = null;
                }
            }
            LOGGER.info(this.getClass().getSimpleName() + " thread completed");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
