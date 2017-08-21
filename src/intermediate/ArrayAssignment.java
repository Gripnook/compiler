package intermediate;

import symbols.Array;
import symbols.Type;

public class ArrayAssignment extends Statement {
    protected Id array;
    protected Expression index;
    protected Expression expr;

    protected ArrayAssignment(ArrayAccess access, Expression expr, int lexline) {
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
        Expression index = this.index.reduce();
        Expression expr = this.expr.reduce();
        generator.emitArrayAssignment(array, index, expr);
    }
}
