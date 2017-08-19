package inter;

public class Break extends Stmt {
    protected Stmt enclosing;

    protected Break(Stmt enclosing, int lexline) {
        super(lexline);
        if (enclosing.equals(Stmt.NULL))
            error("unenclosed break");
        this.enclosing = enclosing;
    }

    @Override
    public void generate(int begin, int after) {
        emit("goto L" + enclosing.after);
    }
}
