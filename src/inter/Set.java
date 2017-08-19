package inter;

import symbols.Type;

public class Set extends Stmt {
    protected Id id;
    protected Expr expr;

    protected Set(Id id, Expr expr, int lexline) {
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
