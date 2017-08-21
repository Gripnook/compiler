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
        int label1 = generator.createLabel();
        int label2 = generator.createLabel();
        expr.jumping(0, label2);
        generator.emitLabel(label1);
        stmt1.generate(label1, after);
        generator.emitGoto(after);
        generator.emitLabel(label2);
        stmt2.generate(label2, after);
    }
}
