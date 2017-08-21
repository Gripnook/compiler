package intermediate;

import lexer.Word;
import symbols.Type;

public class Id extends Expression {
    protected int offset; // Relative address.

    protected Id(Word word, Type type, int offset, int lexline) {
        super(word, type, lexline);
        this.offset = offset;
    }

    @Override
    public String toString() {
        return token.toString() + "." + offset;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Id) && offset == ((Id) other).offset;
    }
}
