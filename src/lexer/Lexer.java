package lexer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lexer {
    private static final String numberPattern = "\\d*\\.?\\d+([eE][-+]?\\d+)?";
    private static final String lexemePattern = "[a-zA-Z_]\\w*";
    private static final String tokenPattern = "==|!=|<=|>=|[-+*/%.()=;<>]";

    public int line = 1;

    private Scanner in;
    private Map<String, Word> words = new HashMap<>();

    public Lexer(Reader in) {
        this.in = new Scanner(in);

        reserve(Tag.TRUE, "true");
        reserve(Tag.FALSE, "false");
    }

    public Lexer(InputStream in) {
        this(new InputStreamReader(in));
    }

    private void reserve(int tag, String lexeme) {
        words.put(lexeme, new Word(tag, lexeme));
    }

    public Token scan() {
        skipWhitespace();
        skipComments();

        String next = null;
        if ((next = scan(numberPattern)) != null) {
            double value = Double.parseDouble(next);
            return new Num(value);
        } else if ((next = scan(lexemePattern)) != null) {
            String lexeme = next;
            Word word = words.get(lexeme);
            if (word == null) {
                word = new Word(Tag.ID, lexeme);
                words.put(lexeme, word);
            }
            return word;
        } else if ((next = scan(tokenPattern)) != null) {
            int tag = getTag(next);
            return new Token(tag);
        } else {
            throw new Error("Bad token");
        }
    }

    private void skipWhitespace() {
        String next = null;
        while ((next = scan("\\s")) != null) {
            if (next.equals("\n"))
                ++line;
        }
    }

    private void skipComments() {
        while (true) {
            if (scan("//.*\n") != null) {
                ++line;
                skipWhitespace();
            } else if (scan("/\\*") != null) {
                while (scan("[^\n]*\\*/") == null) {
                    scan(".*\n");
                    ++line;
                }
                skipWhitespace();
            } else {
                break;
            }
        }
    }

    private String scan(String regex) {
        return in.findWithinHorizon("\\G" + regex, 0);
    }

    private int getTag(String terminal) {
        switch (terminal) {
        case "==":
            return Tag.EQUAL;
        case "!=":
            return Tag.NOT_EQUAL;
        case "<=":
            return Tag.LESS_THAN_OR_EQUAL;
        case ">=":
            return Tag.GREATER_THAN_OR_EQUAL;
        default:
            return terminal.charAt(0);
        }
    }
}
