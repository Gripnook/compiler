package lexer;

public class Tag {
    // Type identifiers.
    public static final int INVALID = 256, BASIC = 257, NUM = 258, REAL = 259, ID = 260;

    // Reserved keywords.
    public static final int TRUE = 320, FALSE = 321, IF = 322, ELSE = 323, DO = 324, WHILE = 325, BREAK = 326;

    // Terminals.
    public static final int AND = 384, OR = 385, EQUAL = 386, NOT_EQUAL = 387, LESS_THAN_OR_EQUAL = 388,
            GREATER_THAN_OR_EQUAL = 389;

    // Internal.
    public static final int INDEX = 448, MINUS = 449, TEMP = 450;

    private Tag() {

    }
}
