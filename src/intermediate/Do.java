package intermediate;

import symbols.Type;

public class Do extends Statement {
    protected Expression expr;
    protected Statement stmt;

    protected Do(int lexline) {
        super(lexline);
        expr = null;
        stmt = null;
    }

    public void init(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in do");
    }

    @Override
    public void generate(int begin, int after) {
        this.after = after; // Save label after.
        int label = createLabel(); // Label for expression.
        stmt.generate(begin, label);
        emitLabel(label);
        expr.jumping(begin, 0);
    }
}
