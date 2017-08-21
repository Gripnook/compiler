package intermediate;

import lexer.Token;
import lexer.Word;
import symbols.Type;

public class Logical extends Expression {
    protected Expression lhs, rhs;

    protected Logical(Token op, Expression lhs, Expression rhs, int lexline) {
        super(op, null, lexline);
        this.lhs = lhs;
        this.rhs = rhs;
        type = check(lhs.type, rhs.type);
        if (type == null)
            error("type error");
    }

    protected Type check(Type lhs, Type rhs) {
        if (lhs.equals(Type.BOOL) && rhs.equals(Type.BOOL))
            return Type.BOOL;
        else
            return null;
    }

    @Override
    public Expression generate() {
        int f = generator.createLabel();
        int after = generator.createLabel();
        Temp temp = factory.createTemp(type, lexline);
        jumping(0, f);
        generator.emitAssignment(temp, factory.createConstant(Word.TRUE, Type.BOOL, lexline));
        generator.emitGoto(after);
        generator.emitLabel(f);
        generator.emitAssignment(temp, factory.createConstant(Word.FALSE, Type.BOOL, lexline));
        generator.emitLabel(after);
        return temp;
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + token.toString() + " " + rhs.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Logical) && lhs.equals(((Logical) other).lhs)
                && rhs.equals(((Logical) other).rhs);
    }
}
