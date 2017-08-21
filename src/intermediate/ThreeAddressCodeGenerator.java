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
    public void emit(String instruction) {
        out.println("\t" + instruction);
    }
}