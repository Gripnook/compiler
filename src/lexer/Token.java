package lexer;

public class Token {
    public final int tag;

    public Token(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "" + (char) tag;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Token) && tag == ((Token) other).tag;
    }
}
