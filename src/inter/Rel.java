package inter;

import lexer.Token;
import symbols.Array;
import symbols.Type;

public class Rel extends Logical {
    protected Rel(Token op, Expr lhs, Expr rhs, int lexline) {
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
        Expr a = lhs.reduce();
        Expr b = rhs.reduce();
        String test = a.toString() + " " + token.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Rel);
    }
}
