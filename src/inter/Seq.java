package inter;

public class Seq extends Stmt {
    protected Stmt stmt1, stmt2;

    protected Seq(Stmt stmt1, Stmt stmt2, int lexline) {
        super(lexline);
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public void generate(int begin, int after) {
        if (stmt1.equals(Stmt.NULL)) {
            stmt2.generate(begin, after);
        } else if (stmt2.equals(Stmt.NULL)) {
            stmt1.generate(begin, after);
        } else {
            int label = createLabel();
            stmt1.generate(begin, label);
            emitLabel(label);
            stmt2.generate(label, after);
        }
    }
}
