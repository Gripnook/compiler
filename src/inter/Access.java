package inter;

import lexer.Tag;
import lexer.Word;
import symbols.Type;

public class Access extends Op {
    protected Id array;
    protected Expr index;

    protected Access(Id array, Expr index, Type type, int lexline) {
        super(new Word(Tag.INDEX, "[]"), type, lexline);
        this.array = array;
        this.index = index;
    }

    @Override
    public Expr generate() {
        return factory.createAccess(array, index.reduce(), type, lexline);
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
        return super.equals(other) && (other instanceof Access) && array.equals(((Access) other).array)
                && index.equals(((Access) other).index);
    }
}
