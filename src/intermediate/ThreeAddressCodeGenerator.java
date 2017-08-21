package intermediate;

import java.io.OutputStream;
import java.io.PrintStream;

public class ThreeAddressCodeGenerator extends IntermediateCodeGenerator {
    private PrintStream out;

    public ThreeAddressCodeGenerator(OutputStream out) {
        this.out = new PrintStream(out);
    }

    @Override
    public void emitLabel(int label) {
        out.print("L" + label + ":");
    }

    @Override
    public void emitGoto(int label) {
        emit("goto L" + label);
    }

    @Override
    public void emitIf(String test, int label) {
        emit("if " + test + " goto L" + label);
    }

    @Override
    public void emitIfFalse(String test, int label) {
        emit("iffalse " + test + " goto L" + label);
    }

    @Override
    public void emitAssignment(Temp temp, Expression expr) {
        emit(temp.toString() + " = " + expr.toString());
    }

    @Override
    public void emitAssignment(Id id, Expression expr) {
        emit(id.toString() + " = " + expr.toString());
    }

    @Override
    public void emitArrayAssignment(Id array, Expression index, Expression expr) {
        emit(array.toString() + "[" + index.toString() + "] = " + expr.toString());
    }

    private void emit(String instruction) {
        out.println("\t" + instruction);
    }
}
