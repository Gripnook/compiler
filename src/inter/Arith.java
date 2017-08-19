package inter;

import lexer.Token;
import symbols.Type;

public class Arith extends Op {
    protected Expr lhs, rhs;

    protected Arith(Token op, Expr lhs, Expr rhs, int lexline) {
        super(op, null, lexline);
        this.lhs = lhs;
        this.rhs = rhs;
        type = Type.max(lhs.type, rhs.type);
        if (type == null)
            error("type error");
    }

    @Override
    public Expr generate() {
        return factory.createArith(token, lhs.reduce(), rhs.reduce(), lexline);
    }

    @Override
    public String toString() {
        return lhs.toString() + " " + token.toString() + " " + rhs.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Arith) && lhs.equals(((Arith) other).lhs)
                && rhs.equals(((Arith) other).rhs);
    }
}
