package server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;


public class ResultSender implements Runnable {
    private final DatagramChannel channel = NetworkManager.channel;
    ByteBuffer resultBuffer;
    Logger LOGGER = Logger.getLogger("sender");
    private byte[] resultArr;
    private SocketAddress destinationAddress;

    public ResultSender(byte[] resultArr, SocketAddress destinationAddress) {
        this.resultArr = resultArr;
        this.destinationAddress = destinationAddress;
    }

    @Override
    public void run() {
        LOGGER.info(getClass().getSimpleName() + " thread started");
        try {
            LOGGER.info("sending result size to " + destinationAddress);
            if (sendResultSize(resultArr)) {
                resultBuffer = ByteBuffer.wrap(resultArr);
                LOGGER.info("wrapped " + new String(resultArr));
                channel.send(resultBuffer, destinationAddress);
                LOGGER.info(new String(resultArr) + "\nsent");
                resultArr = null;
                destinationAddress = null;
                resultBuffer.clear();
            } else LOGGER.info("result sending failed");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("result sending failed");
        } finally {
            LOGGER.info(getClass().getSimpleName() + " thread completed\n\n");
        }
    }

    private boolean sendResultSize(byte[] resultArr) {
        try {
            int resultSize = resultArr.length;
            ByteBuffer resultSizeBuffer = ByteBuffer.allocate(4).putInt(resultSize);
            resultSizeBuffer.flip();
            channel.send(resultSizeBuffer, destinationAddress);
            LOGGER.info("result size = " + resultSize + " sent to client at " + destinationAddress);
            return true;
        } catch (IOException e) {
            LOGGER.info("failed to send result array");
            return false;
        }
    }
}
