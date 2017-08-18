package symbols;

import lexer.Tag;

public class Array extends Type {
    public final Type baseType;
    public final int size;

    public Array(Type baseType, int size) {
        super(Tag.INDEX, "[]", size * baseType.width);
        this.baseType = baseType;
        this.size = size;
    }

    @Override
    public String toString() {
        return baseType.toString() + "[" + size + "]";
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof Array) && baseType.equals(((Array) other).baseType)
                && size == ((Array) other).size;
    }
}
