package intermediate;

import lexer.Token;
import symbols.Type;

public class Expression extends Node {
    public Token token;
    public Type type;

    protected Expression(Token token, Type type, int lexline) {
        super(lexline);
        this.token = token;
        this.type = type;
    }

    public Expression generate() {
        return this;
    }

    public Expression reduce() {
        return this;
    }

    public void jumping(int t, int f) {
        emitJumps(toString(), t, f);
    }

    public void emitJumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        } else if (t != 0) {
            emit("if " + test + " goto L" + t);
        } else if (f != 0) {
            emit("iffalse " + test + " goto L" + f);
        } else {
            // Nothing since both t and f fall through.
        }
    }

    @Override
    public String toString() {
        return token.toString();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Expression) && token.equals(((Expression) other).token)
                && type.equals(((Expression) other).type);
    }
}
