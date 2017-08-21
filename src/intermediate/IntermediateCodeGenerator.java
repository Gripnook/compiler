package intermediate;

public abstract class IntermediateCodeGenerator {
    private int labels = 0;

    public int createLabel() {
        return ++labels;
    }

    public abstract void emitLabel(int label);

    public abstract void emitGoto(int label);

    public abstract void emitIf(Expression test, int label);

    public abstract void emitIfFalse(Expression test, int label);

    public abstract void emitAssignment(Temp temp, Expression expr);

    public abstract void emitAssignment(Id id, Expression expr);

    public abstract void emitArrayAssignment(Id array, Expression index, Expression expr);
}
