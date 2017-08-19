package lexer;

import java.io.StringReader;

import org.testng.Assert;
import org.testng.annotations.Test;

import symbols.Type;

public class TestLexer {
    @Test
    public void testScansNumbers() {
        StringReader in = new StringReader("100 42 1.0e+2 3.14 .1e-0 6.022E23 42.");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Num(100));
        Assert.assertEquals(lex.scan(), new Num(42));
        Assert.assertEquals(lex.scan(), new Real(1.0e+2));
        Assert.assertEquals(lex.scan(), new Real(3.14));
        Assert.assertEquals(lex.scan(), new Real(.1e-0));
        Assert.assertEquals(lex.scan(), new Real(6.022E23));
        Assert.assertEquals(lex.scan(), new Real(42.));
    }

    @Test
    public void testScansKeywords() {
        StringReader in = new StringReader("if else do while break true false int float char bool");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Word(Tag.IF, "if"));
        Assert.assertEquals(lex.scan(), new Word(Tag.ELSE, "else"));
        Assert.assertEquals(lex.scan(), new Word(Tag.DO, "do"));
        Assert.assertEquals(lex.scan(), new Word(Tag.WHILE, "while"));
        Assert.assertEquals(lex.scan(), new Word(Tag.BREAK, "break"));
        Assert.assertEquals(lex.scan(), Word.TRUE);
        Assert.assertEquals(lex.scan(), Word.FALSE);
        Assert.assertEquals(lex.scan(), Type.INT);
        Assert.assertEquals(lex.scan(), Type.FLOAT);
        Assert.assertEquals(lex.scan(), Type.CHAR);
        Assert.assertEquals(lex.scan(), Type.BOOL);
    }

    @Test
    public void testScansIdentifiers() {
        StringReader in = new StringReader("x y __id__ a0");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "y"));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "__id__"));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "a0"));
    }

    @Test
    public void testScansOperators() {
        StringReader in = new StringReader("+-*/()=!;<>[] == != <= >= && ||");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Token('+'));
        Assert.assertEquals(lex.scan(), new Token('-'));
        Assert.assertEquals(lex.scan(), new Token('*'));
        Assert.assertEquals(lex.scan(), new Token('/'));
        Assert.assertEquals(lex.scan(), new Token('('));
        Assert.assertEquals(lex.scan(), new Token(')'));
        Assert.assertEquals(lex.scan(), new Token('='));
        Assert.assertEquals(lex.scan(), new Token('!'));
        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.scan(), new Token('<'));
        Assert.assertEquals(lex.scan(), new Token('>'));
        Assert.assertEquals(lex.scan(), new Token('['));
        Assert.assertEquals(lex.scan(), new Token(']'));
        Assert.assertEquals(lex.scan(), Word.EQUAL);
        Assert.assertEquals(lex.scan(), Word.NOT_EQUAL);
        Assert.assertEquals(lex.scan(), Word.LESS_THAN_OR_EQUAL);
        Assert.assertEquals(lex.scan(), Word.GREATER_THAN_OR_EQUAL);
        Assert.assertEquals(lex.scan(), Word.AND);
        Assert.assertEquals(lex.scan(), Word.OR);
    }

    @Test
    public void testBadTokenReturnsNull() {
        StringReader in = new StringReader("");
        Lexer lex = new Lexer(in);

        Assert.assertNull(lex.scan());
    }

    @Test
    public void testSkipsWhitespace() {
        StringReader in = new StringReader("\t\t x+y ;");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Token('+'));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "y"));
        Assert.assertEquals(lex.scan(), new Token(';'));
    }

    @Test
    public void testCountsNewlines() {
        StringReader in = new StringReader("\r\n\r\n\r\n ;");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.getLine(), 4);
    }

    @Test
    public void testSkipsLineComments() {
        StringReader in = new StringReader("// This is a comment.\n" + "x = 0; // Define x.\n" + "print(x);\n");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Token('='));
        Assert.assertEquals(lex.scan(), new Num(0));
        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "print"));
        Assert.assertEquals(lex.scan(), new Token('('));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Token(')'));
        Assert.assertEquals(lex.scan(), new Token(';'));
    }

    @Test
    public void testLineCommentsAddToLineCount() {
        StringReader in = new StringReader("// Comments.\n//\n//\n ;");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.getLine(), 4);
    }

    @Test
    public void testSkipsBlockComments() {
        StringReader in = new StringReader("/* This is a comment. */\n" + "x = 0; /* Define x. */\n" + "print(x);\n");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Token('='));
        Assert.assertEquals(lex.scan(), new Num(0));
        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "print"));
        Assert.assertEquals(lex.scan(), new Token('('));
        Assert.assertEquals(lex.scan(), new Word(Tag.ID, "x"));
        Assert.assertEquals(lex.scan(), new Token(')'));
        Assert.assertEquals(lex.scan(), new Token(';'));
    }

    @Test
    public void testBlockCommentsAddToLineCount() {
        StringReader in = new StringReader("/* This \n is \n a \n multi-line \n comment. */\n ;");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Token(';'));
        Assert.assertEquals(lex.getLine(), 6);
    }

    @Test
    public void testCombiningCommentTypes() {
        StringReader in = new StringReader("/* This is a comment. */ // This is also a comment.\n ;");
        Lexer lex = new Lexer(in);

        Assert.assertEquals(lex.scan(), new Token(';'));
    }
}
