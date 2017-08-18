package lexer;

public class Real extends Token {
    public final double value;

    public Real(double value) {
        super(Tag.REAL);
        this.value = value;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Real) && value == ((Real) other).value;
    }
}
