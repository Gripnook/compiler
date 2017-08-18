package symbols;

import lexer.Tag;
import lexer.Word;

public class Type extends Word {
    public static final Type INT = new Type(Tag.BASIC, "int", 4);
    public static final Type CHAR = new Type(Tag.BASIC, "char", 1);
    public static final Type BOOL = new Type(Tag.BASIC, "bool", 1);
    public static final Type FLOAT = new Type(Tag.BASIC, "float", 8);

    public static boolean numeric(Type t) {
        return t.equals(Type.INT) || t.equals(Type.CHAR) || t.equals(Type.FLOAT);
    }

    public static Type max(Type t1, Type t2) {
        if (!numeric(t1) || !numeric(t2))
            return null;
        else if (t1.equals(Type.FLOAT) || t2.equals(Type.FLOAT))
            return Type.FLOAT;
        else if (t1.equals(Type.INT) || t2.equals(Type.INT))
            return Type.INT;
        else
            return Type.CHAR;
    }

    public final int width;

    public Type(int tag, String type, int width) {
        super(tag, type);
        this.width = width;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Type) && width == ((Type) other).width;
    }
}
