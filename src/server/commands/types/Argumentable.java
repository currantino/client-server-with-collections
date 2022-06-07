package server.commands.types;

import mid.ServerRequest;

public interface Argumentable {
    String execute(ServerRequest request);
    void setArgument(ServerRequest request);
}
