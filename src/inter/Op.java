package inter;

import lexer.Token;
import symbols.Type;

public class Op extends Expr {
    protected Op(Token op, Type type, int lexline) {
        super(op, type, lexline);
    }

    @Override
    public Expr reduce() {
        Expr x = generate();
        Temp t = factory.createTemp(type, lexline);
        emit(t.toString() + " = " + x.toString());
        return t;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Op);
    }
}
