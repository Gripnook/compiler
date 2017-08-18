package lexer;

import java.io.StringReader;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestLexer {
    @Test
    public void testScansNumbers() {
        StringReader in = new StringReader("100 42 1.0e+2 3.14 .1e-0 6.022E23");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 100.0);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 42.0);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 1.0e+2);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 3.14);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, .1e-0);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 6.022E23);
    }

    @Test
    public void testScansKeywords() {
        StringReader in = new StringReader("true false");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.TRUE);
        Assert.assertEquals(((Word) t).lexeme, "true");

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.FALSE);
        Assert.assertEquals(((Word) t).lexeme, "false");
    }

    @Test
    public void testScansIdentifiers() {
        StringReader in = new StringReader("x y __id__ a0");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "y");

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "__id__");

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "a0");
    }

    @Test
    public void testScansOperators() {
        StringReader in = new StringReader("+-*/%.()=;<> == != <= >=");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, '+');

        t = lex.scan();
        Assert.assertEquals(t.tag, '-');

        t = lex.scan();
        Assert.assertEquals(t.tag, '*');

        t = lex.scan();
        Assert.assertEquals(t.tag, '/');

        t = lex.scan();
        Assert.assertEquals(t.tag, '%');

        t = lex.scan();
        Assert.assertEquals(t.tag, '.');

        t = lex.scan();
        Assert.assertEquals(t.tag, '(');

        t = lex.scan();
        Assert.assertEquals(t.tag, ')');

        t = lex.scan();
        Assert.assertEquals(t.tag, '=');

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');

        t = lex.scan();
        Assert.assertEquals(t.tag, '<');

        t = lex.scan();
        Assert.assertEquals(t.tag, '>');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.EQUAL);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NOT_EQUAL);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.LESS_THAN_OR_EQUAL);

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.GREATER_THAN_OR_EQUAL);
    }

    @Test
    public void testBadTokenReturnsNull() {
        StringReader in = new StringReader("");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertNull(t);
    }

    @Test
    public void testSkipsWhitespace() {
        StringReader in = new StringReader("\t\t x+y ;");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, '+');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "y");

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');
    }

    @Test
    public void testCountsNewlines() {
        StringReader in = new StringReader("\r\n\r\n\r\n ;");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, ';');
        Assert.assertEquals(lex.line, 4);
    }

    @Test
    public void testSkipsLineComments() {
        StringReader in = new StringReader("// This is a comment.\n" + "x = 0; // Define x.\n" + "print(x);\n");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, '=');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 0.0);

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "print");

        t = lex.scan();
        Assert.assertEquals(t.tag, '(');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, ')');

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');
    }

    @Test
    public void testSkipsBlockComments() {
        StringReader in = new StringReader("/* This is a comment. */\n" + "x = 0; /* Define x. */\n" + "print(x);\n");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, '=');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.NUM);
        Assert.assertEquals(((Num) t).value, 0.0);

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "print");

        t = lex.scan();
        Assert.assertEquals(t.tag, '(');

        t = lex.scan();
        Assert.assertEquals(t.tag, Tag.ID);
        Assert.assertEquals(((Word) t).lexeme, "x");

        t = lex.scan();
        Assert.assertEquals(t.tag, ')');

        t = lex.scan();
        Assert.assertEquals(t.tag, ';');
    }

    @Test
    public void testBlockCommentsAddToLineCount() {
        StringReader in = new StringReader("/* This \n is \n a \n multi-line \n comment. */\n ;");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, ';');
        Assert.assertEquals(lex.line, 6);
    }

    @Test
    public void testCombiningCommentTypes() {
        StringReader in = new StringReader("/* This is a comment. */ // This is also a comment.\n ;");
        Lexer lex = new Lexer(in);

        Token t = lex.scan();
        Assert.assertEquals(t.tag, ';');
    }
}
