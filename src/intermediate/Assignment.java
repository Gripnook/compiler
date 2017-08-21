package intermediate;

import symbols.Type;

public class Assignment extends Statement {
    protected Id id;
    protected Expression expr;

    protected Assignment(Id id, Expression expr, int lexline) {
        super(lexline);
        this.id = id;
        this.expr = expr;
        if (check(id.type, expr.type) == null)
            error("type error");
    }

    protected Type check(Type lhs, Type rhs) {
        if (Type.numeric(lhs) && Type.numeric(rhs))
            return rhs;
        else if (lhs.equals(Type.BOOL) && rhs.equals(Type.BOOL))
            return rhs;
        else
            return null;
    }

    @Override
    public void generate(int begin, int after) {
        emit(id.toString() + " = " + expr.generate().toString());
    }
}
