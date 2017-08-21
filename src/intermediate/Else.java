package intermediate;

import symbols.Type;

public class Else extends Statement {
    protected Expression expr;
    protected Statement stmt1, stmt2;

    protected Else(Expression expr, Statement stmt1, Statement stmt2, int lexline) {
        super(lexline);
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        if (!expr.type.equals(Type.BOOL))
            error("boolean required in if");
    }

    @Override
    public void generate(int begin, int after) {
        int label1 = createLabel(); // Label for the code in statement 1.
        int label2 = createLabel(); // Label for the code in statement 2.
        expr.jumping(0, label2);
        emitLabel(label1);
        stmt1.generate(label1, after);
        emit("goto L" + after);
        emitLabel(label2);
        stmt2.generate(label2, after);
    }
}
