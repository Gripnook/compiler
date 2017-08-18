package lexer;

public class Num extends Token {
    public final int value;

    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Num) && value == ((Num) other).value;
    }
}
