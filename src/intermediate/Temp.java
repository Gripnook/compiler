package intermediate;

import lexer.Word;
import symbols.Type;

public class Temp extends Expression {
    protected int number;

    protected Temp(Type type, int number, int lexline) {
        super(Word.TEMP, type, lexline);
        this.number = number;
    }

    @Override
    public String toString() {
        return "$t" + number;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Temp) && number == ((Temp) other).number;
    }
}
