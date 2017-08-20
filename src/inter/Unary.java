package inter;

import lexer.Token;
import symbols.Type;

public class Unary extends Op {
    protected Expression expr;

    protected Unary(Token op, Expression expr, int lexline) {
        super(op, null, lexline);
        type = Type.max(Type.INT, expr.type);
        if (type == null)
            error("type error");
    }

    @Override
    public Expression generate() {
        return factory.createUnary(token, expr.reduce(), lexline);
    }

    @Override
    public String toString() {
        return token.toString() + " " + expr.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Unary) && expr.equals(((Unary) other).expr);
    }
}
