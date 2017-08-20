package inter;

import lexer.Token;

public class And extends Logical {
    protected And(Token op, Expression lhs, Expression rhs, int lexline) {
        super(op, lhs, rhs, lexline);
    }

    @Override
    public void jumping(int t, int f) {
        int label = f != 0 ? f : createLabel();
        lhs.jumping(0, label);
        rhs.jumping(t, f);
        if (f == 0)
            emitLabel(label);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof And);
    }
}
