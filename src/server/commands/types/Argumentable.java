package server.commands.types;

public interface Argumentable<T> {
    String execute(T argument);
}
