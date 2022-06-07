package server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import static server.NetworkManager.LOGGER;


public class ResultSender implements Runnable {
    private byte[] resultArr;
    private SocketAddress destinationAddress;
    private DatagramChannel channel;

    public ResultSender(byte[] resultArr, SocketAddress destinationAddress, DatagramChannel channel) {
        this.resultArr = resultArr;
        this.destinationAddress = destinationAddress;
        this.channel = channel;
    }

    @Override
    public void run() {
        LOGGER.info(getClass().getSimpleName() + " thread started");
        try {
            ByteBuffer resultBuffer = ByteBuffer.wrap(resultArr);
            channel.send(resultBuffer, destinationAddress);
            LOGGER.info(new String(resultArr) + "\nsent");
            resultArr = null;
            destinationAddress = null;
            resultBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info(getClass().getSimpleName() + " thread completed\n\n");
        }
    }
}
