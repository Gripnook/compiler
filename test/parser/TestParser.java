package parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.*;

import intermediate.IntermediateCodeGenerator;
import intermediate.ThreeAddressCodeGenerator;
import lexer.Lexer;

public class TestParser {
    @Test
    public void test() throws IOException {
        StringReader in = new StringReader(program());
        Lexer lex = new Lexer(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IntermediateCodeGenerator generator = new ThreeAddressCodeGenerator(out);
        Parser parse = new Parser(lex, generator);
        parse.program();
        Assert.assertEquals(out.toString(), expected());
    }

    private String program() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream program = new PrintStream(buffer);

        program.println("{");
        program.println("    int i; int j; float v; float x; float[100] a;");
        program.println("    while (true) {");
        program.println("        do i = i+1; while (a[i] < v);");
        program.println("        do j = j-1; while (a[j] > v);");
        program.println("        if (i >= j) break;");
        program.println("        x = a[i]; a[i] = a[j]; a[j] = x;");
        program.println("    }");
        program.println("}");

        return buffer.toString();
    }

    private String expected() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream expected = new PrintStream(buffer);

        expected.println("L1:L3:\ti.0 = i.0 + 1");
        expected.println("L5:\t$t1 = i.0 * 8");
        expected.println("\t$t2 = a.24[ $t1 ]");
        expected.println("\tif $t2 < v.8 goto L3");
        expected.println("L4:\tj.4 = j.4 - 1");
        expected.println("L7:\t$t3 = j.4 * 8");
        expected.println("\t$t4 = a.24[ $t3 ]");
        expected.println("\tif $t4 > v.8 goto L4");
        expected.println("L6:\tiffalse i.0 >= j.4 goto L8");
        expected.println("L9:\tgoto L2");
        expected.println("L8:\t$t5 = i.0 * 8");
        expected.println("\tx.16 = a.24[ $t5 ]");
        expected.println("L10:\t$t6 = i.0 * 8");
        expected.println("\t$t7 = j.4 * 8");
        expected.println("\t$t8 = a.24[ $t7 ]");
        expected.println("\ta.24[ $t6 ] = $t8");
        expected.println("L11:\t$t9 = j.4 * 8");
        expected.println("\ta.24[ $t9 ] = x.16");
        expected.println("\tgoto L1");
        expected.print("L2:");

        return buffer.toString();
    }
}
