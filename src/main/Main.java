package main;

import java.io.FileInputStream;
import java.io.IOException;

import intermediate.IntermediateCodeGenerator;
import intermediate.ThreeAddressCodeGenerator;
import lexer.Lexer;
import parser.Parser;

public class Main {
    public static void main(String[] args) throws IOException {
        try (FileInputStream in = new FileInputStream("test_program")) {
            Lexer lex = new Lexer(in);
            IntermediateCodeGenerator generator = new ThreeAddressCodeGenerator(System.out);
            Parser parse = new Parser(lex, generator);
            parse.program();
            System.out.println();
        }
    }
}
