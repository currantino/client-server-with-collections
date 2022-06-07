package server;

import mid.ServerRequest;
import server.commands.Command;
import server.commands.types.NotCheckable;
import server.commands.types.Readable;
import server.commands.types.Writable;
import server.data.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static server.JdbcServer.pdb;

public class RequestProcessor implements Runnable {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();

    private ServerRequest request;
    private String result;


    {
        result = "unknown command, use 'help'";
    }

    public RequestProcessor(ServerRequest request) {
        this.request = request;
    }

    private void processRequest(ServerRequest request) {
        String command = request.getCommand();
        Object argument = request.getArgument();
        String login = request.getLogin();
        String password = request.getPassword();
        if (Data.getCommands().containsKey(command)) {
            Command commandToExecute = Data.getCommands().get(command);
            if (commandToExecute instanceof NotCheckable) {
                result = commandToExecute.execute();
            } else if (pdb.checkLogin(login)) {
                if (pdb.checkPassword(login, password)) {
                    processCommand(commandToExecute);
                }
            } else {
                result = "wrong password";
            }
        } else {
            result = "unknown user: use 'register'";
        }
    }

    private void processCommand(Command command) {
        if (command instanceof Readable) {
            r.lock();
            try {
                result = command.execute();
            } finally {
                r.unlock();
            }
        } else if (command instanceof Writable) {
            w.lock();
            try {
                result = command.execute();
            } finally {
                w.unlock();
            }
        }
    }


    @Override
    public void run() {
            processRequest(request);
    }
}