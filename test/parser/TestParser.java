package parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.testng.Assert;
import org.testng.annotations.Test;

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
        expected.println("\t$t2 = a.24[$t1]");
        expected.println("\t$t3 = $t2 < v.8");
        expected.println("\tif $t3 goto L3");
        expected.println("L4:\tj.4 = j.4 - 1");
        expected.println("L7:\t$t4 = j.4 * 8");
        expected.println("\t$t5 = a.24[$t4]");
        expected.println("\t$t6 = $t5 > v.8");
        expected.println("\tif $t6 goto L4");
        expected.println("L6:\t$t7 = i.0 >= j.4");
        expected.println("\tiffalse $t7 goto L8");
        expected.println("L9:\tgoto L2");
        expected.println("L8:\t$t8 = i.0 * 8");
        expected.println("\tx.16 = a.24[$t8]");
        expected.println("L10:\t$t9 = i.0 * 8");
        expected.println("\t$t10 = j.4 * 8");
        expected.println("\t$t11 = a.24[$t10]");
        expected.println("\ta.24[$t9] = $t11");
        expected.println("L11:\t$t12 = j.4 * 8");
        expected.println("\ta.24[$t12] = x.16");
        expected.println("\tgoto L1");
        expected.print("L2:");

        return buffer.toString();
    }
}
