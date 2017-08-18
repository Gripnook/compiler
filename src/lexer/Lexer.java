package lexer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import symbols.Type;

public class Lexer {
    private static final Pattern numberPattern = Pattern.compile("\\G(\\d+\\.?\\d*|\\.\\d+)([eE][-+]?\\d+)?");
    private static final Pattern lexemePattern = Pattern.compile("\\G[a-zA-Z_]\\w*");
    private static final Pattern tokenPattern = Pattern.compile("\\G(&&|\\|\\||==|!=|<=|>=|[-+*/()=!;<>\\[\\]])");
    private static final Pattern whitespacePattern = Pattern.compile("\\G\\s");
    private static final Pattern lineCommentPattern = Pattern.compile("\\G//.*\n");
    private static final Pattern blockCommentStartPattern = Pattern.compile("\\G/\\*");
    private static final Pattern blockCommentEndPattern = Pattern.compile("\\G[^\n]*\\*/");
    private static final Pattern linePattern = Pattern.compile("\\G.*\n");

    public int line = 1;

    private Scanner in;
    private Map<String, Word> words = new HashMap<>();

    public Lexer(Reader in) {
        this.in = new Scanner(in);

        reserve(Tag.IF, "if");
        reserve(Tag.ELSE, "else");
        reserve(Tag.DO, "do");
        reserve(Tag.WHILE, "while");
        reserve(Tag.BREAK, "break");
        reserve(Word.TRUE);
        reserve(Word.FALSE);
        reserve(Type.INT);
        reserve(Type.CHAR);
        reserve(Type.BOOL);
        reserve(Type.FLOAT);
    }

    public Lexer(InputStream in) {
        this(new InputStreamReader(in));
    }

    private void reserve(int tag, String lexeme) {
        reserve(new Word(tag, lexeme));
    }

    private void reserve(Word word) {
        words.put(word.lexeme, word);
    }

    public Token scan() {
        skipWhitespace();
        skipComments();

        String next = null;
        if ((next = scan(numberPattern)) != null) {
            try {
                int value = Integer.parseInt(next);
                return new Num(value);
            } catch (NumberFormatException e) {
                double value = Double.parseDouble(next);
                return new Real(value);
            }
        } else if ((next = scan(lexemePattern)) != null) {
            String lexeme = next;
            Word word = words.get(lexeme);
            if (word == null) {
                word = new Word(Tag.ID, lexeme);
                words.put(lexeme, word);
            }
            return word;
        } else if ((next = scan(tokenPattern)) != null) {
            return getToken(next);
        } else {
            return null;
        }
    }

    private void skipWhitespace() {
        String next = null;
        while ((next = scan(whitespacePattern)) != null) {
            if (next.equals("\n"))
                ++line;
        }
    }

    private void skipComments() {
        while (true) {
            if (scan(lineCommentPattern) != null) {
                ++line;
                skipWhitespace();
            } else if (scan(blockCommentStartPattern) != null) {
                while (scan(blockCommentEndPattern) == null) {
                    scan(linePattern);
                    ++line;
                }
                skipWhitespace();
            } else {
                break;
            }
        }
    }

    private String scan(Pattern pattern) {
        return in.findWithinHorizon(pattern, 0);
    }

    private Token getToken(String terminal) {
        switch (terminal) {
        case "&&":
            return Word.AND;
        case "||":
            return Word.OR;
        case "==":
            return Word.EQUAL;
        case "!=":
            return Word.NOT_EQUAL;
        case "<=":
            return Word.LESS_THAN_OR_EQUAL;
        case ">=":
            return Word.GREATER_THAN_OR_EQUAL;
        default:
            return new Token(terminal.charAt(0));
        }
    }
}
