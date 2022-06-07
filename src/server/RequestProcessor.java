package server;

import mid.ServerRequest;
import server.commands.Command;
import server.commands.types.Argumentable;
import server.commands.types.NotCheckable;
import server.commands.types.Readable;
import server.commands.types.Writable;
import server.data.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static server.NetworkManager.pdb;


public class RequestProcessor implements Callable<String> {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock r = lock.readLock();
    private final Lock w = lock.writeLock();
    String result;
    ServerRequest request;

    public RequestProcessor(ServerRequest request) {
        this.request = request;
    }

    @Override
    public String call() {
        return processRequest(request);
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

    private void processArgumentableCommand(Argumentable command, Object argument) {
        if (command instanceof Readable) {
            r.lock();
            try {
                result = command.execute(argument);
            } finally {
                r.unlock();
            }
        } else if (command instanceof Writable) {
            w.lock();
            try {
                result = command.execute(argument);
            } finally {
                w.unlock();
            }
        }
    }

    private String processRequest(ServerRequest request) {
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
                    if (commandToExecute instanceof Argumentable)
                        processArgumentableCommand((Argumentable) commandToExecute, argument);
                    else processCommand(commandToExecute);
                    processCommand(commandToExecute);
                }
            } else {
                result = "wrong password";
            }
        } else {
            result = "unknown user: use 'register'";
        }
        return result;
    }
}
