package server.commands;

public abstract class SqlCommand extends Command {

    private String statement;

    public SqlCommand(String name, String desc) {
        super(name, desc);
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
