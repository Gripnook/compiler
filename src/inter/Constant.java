package inter;

import lexer.Num;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public class Constant extends Expression {
    protected static final Constant TRUE = new Constant(Word.TRUE, Type.BOOL, 0);
    protected static final Constant FALSE = new Constant(Word.FALSE, Type.BOOL, 0);

    protected Constant(Token token, Type type, int lexline) {
        super(token, type, lexline);
    }

    protected Constant(int value, int lexline) {
        super(new Num(value), Type.INT, lexline);
    }

    @Override
    public void jumping(int t, int f) {
        if (this.equals(TRUE) && t != 0)
            emit("goto L" + t);
        else if (this.equals(FALSE) && f != 0)
            emit("goto L" + f);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Constant);
    }
}
