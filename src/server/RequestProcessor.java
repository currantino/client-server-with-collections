package server;

import mid.ServerRequest;
import server.commands.Command;
import server.commands.types.Argumentable;
import server.commands.types.NotCheckable;
import server.commands.types.Readable;
import server.commands.types.Writable;
import server.data.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

import static server.NetworkManager.LOGGER;
import static server.NetworkManager.pdb;


public class RequestProcessor implements Runnable {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();
    private byte[] resultArr;
    private String result;
    private ServerRequest request;
    Logger LOGGER = Logger.getLogger("processor");

    public RequestProcessor(ServerRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        LOGGER.info(getClass().getSimpleName() + " thread started");
        resultArr = processRequest(request);
        LOGGER.info("result: \n" + new String(resultArr));
        LOGGER.info(request.toString());
        new Thread(new ResultSender(resultArr, request.getSenderAddress())).start();
        request = null;
        result = null;
        resultArr = null;
        LOGGER.info(getClass().getSimpleName() + " thread completed");
    }

    private byte[] processRequest(ServerRequest request) {
        String command = request.getCommand();
        Object argument = request.getArgument();
        String login = request.getLogin();
        String password = request.getPassword();
        LOGGER.info("executing :\n" + request);
        if (Data.getCommands().containsKey(command)) {
            Command commandToExecute = Data.getCommands().get(command);
            LOGGER.info("executing command " + commandToExecute.getName());

            //NotCheckable
            if (commandToExecute instanceof NotCheckable) {
                LOGGER.info("command is NotCheckable");
                if (commandToExecute instanceof Argumentable) {
                    LOGGER.info("command is Argumentable");
                    return processArgumentableCommand((Argumentable) commandToExecute, request).getBytes();
                } else {
                    LOGGER.info("command is not Argumentable");
                    return processCommand(commandToExecute).getBytes();
                }
            }

            //Checkable
            else if (pdb.checkLogin(login)) {
                LOGGER.info("user " + login + " exists");
                if (pdb.checkPassword(login, password)) {
                    LOGGER.info("password is correct");
                    if (commandToExecute instanceof Argumentable) {
                        LOGGER.info("command is Argumentable");
                        return processArgumentableCommand((Argumentable) commandToExecute, request).getBytes();
                    } else {
                        LOGGER.info("command is not Argumentable");
                        return processCommand(commandToExecute).getBytes();
                    }
                } else {
                    LOGGER.info("wrong password");
                    return "wrong password".getBytes();
                }
            }
            return "unknown user, use 'register'".getBytes();
        }
        return "unknown command, use 'help'".getBytes();
    }

    private String processCommand(Command command) {
        String result = "";
        if (command instanceof Writable) {
            w.lock();
            try {
                result = command.execute();
            } finally {
                LOGGER.info("command is Writable");
                w.unlock();
            }
        } else {
            r.lock();
            try {
                result = command.execute();
            } finally {
                LOGGER.info("command is Readable");
                r.unlock();
            }
        }
        return result;
    }

    private String processArgumentableCommand(Argumentable command, ServerRequest request) {
        String result = "fff";
        if (command instanceof Readable) {
            r.lock();
            try {
                result = command.execute(request);
            } finally {
                LOGGER.info("command is Readable");
                r.unlock();
            }
        } else if (command instanceof Writable) {
            w.lock();
            try {
                result = command.execute(request);
            } finally {
                LOGGER.info("command is Writable");
                w.unlock();
            }
        }
        return result;
    }
}
