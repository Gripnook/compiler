package intermediate;

public class Statements extends Statement {
    protected Statement stmt1, stmt2;

    protected Statements(Statement stmt1, Statement stmt2, int lexline) {
        super(lexline);
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }

    @Override
    public void generate(int begin, int after) {
        if (stmt1.equals(Statement.NULL)) {
            stmt2.generate(begin, after);
        } else if (stmt2.equals(Statement.NULL)) {
            stmt1.generate(begin, after);
        } else {
            int label = generator.createLabel();
            stmt1.generate(begin, label);
            generator.emitLabel(label);
            stmt2.generate(label, after);
        }
    }
}
