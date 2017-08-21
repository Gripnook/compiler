package intermediate;

import lexer.Token;

public class Not extends Logical {
    protected Not(Token op, Expression expr, int lexline) {
        super(op, expr, expr, lexline);
    }

    @Override
    public void jumping(int t, int f) {
        rhs.jumping(f, t);
    }

    @Override
    public String toString() {
        return token.toString() + " " + rhs.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Not);
    }
}
