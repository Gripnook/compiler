package intermediate;

import symbols.Type;

public class While extends Statement {
    protected Expression expr;
    protected Statement stmt;

    protected While(int lexline) {
        super(lexline);
        expr = null;
        stmt = null;
    }

    public void init(Expression expr, Statement stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in while");
    }

    @Override
    public void generate(int begin, int after) {
        this.after = after; // Save label after.
        expr.jumping(0, after);
        int label = generator.createLabel(); // Label for statement.
        generator.emitLabel(label);
        stmt.generate(label, begin);
        generator.emitGoto(begin);
    }
}
