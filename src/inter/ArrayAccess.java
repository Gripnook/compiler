package inter;

import lexer.Tag;
import lexer.Word;
import symbols.Type;

public class ArrayAccess extends Op {
    protected Id array;
    protected Expression index;

    protected ArrayAccess(Id array, Expression index, Type type, int lexline) {
        super(new Word(Tag.INDEX, "[]"), type, lexline);
        this.array = array;
        this.index = index;
    }

    @Override
    public Expression generate() {
        return factory.createArrayAccess(array, index.reduce(), type, lexline);
    }

    @Override
    public void jumping(int t, int f) {
        emitJumps(reduce().toString(), t, f);
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && (other instanceof ArrayAccess) && array.equals(((ArrayAccess) other).array)
                && index.equals(((ArrayAccess) other).index);
    }
}
