package intermediate;

public abstract class IntermediateCodeGenerator {
    private int labels = 0;

    public int createLabel() {
        return ++labels;
    }

    public abstract void emitLabel(int label);

    public abstract void emit(String instruction);
}
