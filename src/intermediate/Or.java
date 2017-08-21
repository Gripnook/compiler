package intermediate;

import lexer.Token;

public class Or extends Logical {
    protected Or(Token op, Expression lhs, Expression rhs, int lexline) {
        super(op, lhs, rhs, lexline);
    }

    @Override
    public void jumping(int t, int f) {
        int label = t != 0 ? t : createLabel();
        lhs.jumping(label, 0);
        rhs.jumping(t, f);
        if (t == 0)
            emitLabel(label);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Or);
    }
}
