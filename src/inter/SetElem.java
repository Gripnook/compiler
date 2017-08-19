package inter;

import symbols.Array;
import symbols.Type;

public class SetElem extends Stmt {
    protected Id array;
    protected Expr index;
    protected Expr expr;

    protected SetElem(Access access, Expr expr, int lexline) {
        super(lexline);
        this.array = access.array;
        this.index = access.index;
        this.expr = expr;
        if (check(access.type, expr.type) == null)
            error("type error");
    }

    protected Type check(Type lhs, Type rhs) {
        if (lhs instanceof Array || rhs instanceof Array)
            return null;
        else if (lhs.equals(rhs))
            return rhs;
        else if (Type.numeric(lhs) && Type.numeric(rhs))
            return rhs;
        else
            return null;
    }

    @Override
    public void generate(int begin, int after) {
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();
        emit(array.toString() + "[" + s1 + "] = " + s2);
    }
}
