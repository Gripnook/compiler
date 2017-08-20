package inter;

import symbols.Array;
import symbols.Type;

public class SetArrayElement extends Statement {
    protected Id array;
    protected Expression index;
    protected Expression expr;

    protected SetArrayElement(ArrayAccess access, Expression expr, int lexline) {
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
        String index = this.index.reduce().toString();
        String expr = this.expr.reduce().toString();
        emit(array.toString() + "[" + index + "] = " + expr);
    }
}