package inter;

public class Break extends Statement {
    protected Statement enclosing;

    protected Break(Statement enclosing, int lexline) {
        super(lexline);
        if (enclosing.equals(Statement.NULL))
            error("unenclosed break");
        this.enclosing = enclosing;
    }

    @Override
    public void generate(int begin, int after) {
        emit("goto L" + enclosing.after);
    }
}
