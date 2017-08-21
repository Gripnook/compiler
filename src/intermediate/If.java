package intermediate;

import symbols.Type;

public class If extends Statement {
    protected Expression expr;
    protected Statement stmt;

    protected If(Expression expr, Statement stmt, int lexline) {
        super(lexline);
        this.expr = expr;
        this.stmt = stmt;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in if");
    }

    @Override
    public void generate(int begin, int after) {
        int label = createLabel(); // Label for the code for the statement.
        expr.jumping(0, after);
        emitLabel(label);
        stmt.generate(label, after);
    }
}
