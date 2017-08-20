package inter;

import lexer.Token;
import symbols.Array;
import symbols.Type;

public class Relational extends Logical {
    protected Relational(Token op, Expression lhs, Expression rhs, int lexline) {
        super(op, lhs, rhs, lexline);
    }

    @Override
    protected Type check(Type lhs, Type rhs) {
        if (lhs instanceof Array || rhs instanceof Array)
            return null;
        else if (lhs.equals(rhs))
            return Type.BOOL;
        else
            return null;
    }

    @Override
    public void jumping(int t, int f) {
        Expression lhs = this.lhs.reduce();
        Expression rhs = this.rhs.reduce();
        String test = lhs.toString() + " " + token.toString() + " " + rhs.toString();
        emitJumps(test, t, f);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Relational);
    }
}
