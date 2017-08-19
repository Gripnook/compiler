package inter;

import lexer.Token;
import symbols.Type;

public class Logical extends Expr {
    protected Expr lhs, rhs;

    protected Logical(Token op, Expr lhs, Expr rhs, int lexline) {
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
    public Expr generate() {
        int f = createLabel();
        int a = createLabel();
        Temp temp = factory.createTemp(type, lexline);
        jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
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
