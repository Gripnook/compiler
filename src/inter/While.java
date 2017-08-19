package inter;

import symbols.Type;

public class While extends Stmt {
    protected Expr expr;
    protected Stmt stmt;

    protected While(int lexline) {
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
        expr.jumping(0, after);
        int label = createLabel(); // Label for statement.
        emitLabel(label);
        stmt.generate(label, begin);
        emit("goto L" + begin);
    }
}
