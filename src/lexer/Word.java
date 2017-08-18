package lexer;

public class Word extends Token {
    public static final Word AND = new Word(Tag.AND, "&&");
    public static final Word OR = new Word(Tag.OR, "||");
    public static final Word EQUAL = new Word(Tag.EQUAL, "==");
    public static final Word NOT_EQUAL = new Word(Tag.NOT_EQUAL, "!=");
    public static final Word LESS_THAN_OR_EQUAL = new Word(Tag.LESS_THAN_OR_EQUAL, "<=");
    public static final Word GREATER_THAN_OR_EQUAL = new Word(Tag.GREATER_THAN_OR_EQUAL, ">=");
    public static final Word MINUS = new Word(Tag.MINUS, "minus");
    public static final Word TRUE = new Word(Tag.TRUE, "true");
    public static final Word FALSE = new Word(Tag.FALSE, "false");
    public static final Word TEMP = new Word(Tag.TEMP, "temp");

    public final String lexeme;

    public Word(int tag, String lexeme) {
        super(tag);
        this.lexeme = new String(lexeme);
    }

    @Override
    public String toString() {
        return lexeme;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Word) && lexeme.equals(((Word) other).lexeme);
    }
}
