package intermediate;

import lexer.Token;
import symbols.Type;

public class Op extends Expression {
    protected Op(Token op, Type type, int lexline) {
        super(op, type, lexline);
    }

    @Override
    public Expression reduce() {
        Expression expr = generate();
        Temp temp = factory.createTemp(type, lexline);
        emit(temp.toString() + " = " + expr.toString());
        return temp;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Op);
    }
}
