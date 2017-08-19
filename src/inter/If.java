package inter;

import symbols.Type;

public class If extends Stmt {
    protected Expr expr;
    protected Stmt stmt;

    protected If(Expr expr, Stmt stmt, int lexline) {
        super(lexline);
        this.expr = expr;
        this.stmt = stmt;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in if");
    }

    @Override
    public void generate(int begin, int after) {
        int label = createLabel(); // Label for the code for the statement.
        expr.jumping(0, after); // Fall through on true, goto after on false.
        emitLabel(label);
        stmt.generate(label, after);
    }
}
