package inter;

public class Stmt extends Node {
    public static final Stmt NULL = new Stmt(0); // The empty sequence of
                                                 // statements.

    protected int after = 0; // Saves label after.

    protected Stmt(int lexline) {
        super(lexline);
    }

    public void generate(int begin, int after) {

    }
}
