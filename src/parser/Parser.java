package parser;

import lexer.Lexer;

// TODO
public class Parser {
    private Lexer lex;

    public Parser(Lexer lex) {
        this.lex = lex;
    }

    public void program() {
        lex.scan();
    }
}
