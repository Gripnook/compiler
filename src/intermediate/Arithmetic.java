package intermediate;

import lexer.Token;
import symbols.Type;

public class Arithmetic extends Op {
    protected Expression lhs, rhs;

    protected Arithmetic(Token op, Expression lhs, Expression rhs, int lexline) {
        super(op, null, lexline);
        this.lhs = lhs;
        this.rhs = rhs;
        type = Type.max(lhs.type, rhs.type);
        if (type == null)
            error("type error");
    }

    @Override
    public Expression generate() {
        return factory.createArithmetic(token, lhs.reduce(), rhs.reduce(), lexline);
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + token.toString() + " " + rhs.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Arithmetic) && lhs.equals(((Arithmetic) other).lhs)
                && rhs.equals(((Arithmetic) other).rhs);
    }
}
