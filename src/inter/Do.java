package inter;

import symbols.Type;

public class Do extends Stmt {
    protected Expr expr;
    protected Stmt stmt;

    protected Do(int lexline) {
        super(lexline);
        expr = null;
        stmt = null;
    }

    protected void init(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in while");
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
