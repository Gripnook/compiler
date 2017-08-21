package intermediate;

public class Statement extends Node {
    // The empty sequence of statements.
    public static final Statement NULL = new Statement(0);

    protected int after = 0; // Saves label after.

    protected Statement(int lexline) {
        super(lexline);
    }

    public void generate(int begin, int after) {

    }
}
