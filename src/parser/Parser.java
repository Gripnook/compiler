package parser;

import inter.ArrayAccess;
import inter.IntermediateCodeGenerator;
import inter.Do;
import inter.Expression;
import inter.Id;
import inter.NodeFactory;
import inter.Statement;
import inter.While;
import lexer.Lexer;
import lexer.Num;
import lexer.Tag;
import lexer.Token;
import lexer.Word;
import symbols.Array;
import symbols.Env;
import symbols.Type;

public class Parser {
    private Lexer lex;
    private IntermediateCodeGenerator generator;
    private NodeFactory factory;
    private Token lookahead;
    private Env env = null;
    private int storageUsed = 0;
    private Statement enclosing = Statement.NULL;

    public Parser(Lexer lex, IntermediateCodeGenerator generator) {
        this.lex = lex;
        this.generator = generator;
        this.factory = new NodeFactory(lex, generator);
        move();
    }

    private void move() {
        lookahead = lex.scan();
    }

    private void match(int tag) {
        if (lookahead.tag == tag)
            move();
        else
            error("syntax error");
    }

    private void error(String message) {
        throw new Error("near line " + lex.getLineNumber() + ": " + message);
    }

    public void program() {
        Statement stmt = block();
        int begin = generator.createLabel();
        int after = generator.createLabel();
        generator.emitLabel(begin);
        stmt.generate(begin, after);
        generator.emitLabel(after);
    }

    private Statement block() {
        match('{');
        Env savedEnv = env;
        env = new Env(env);
        declarations();
        Statement stmt = statements();
        match('}');
        env = savedEnv;
        return stmt;
    }

    private void declarations() {
        while (lookahead.tag == Tag.BASIC) {
            Type type = type();
            Token token = lookahead;
            match(Tag.ID);
            match(';');
            Id id = factory.createId((Word) token, type, storageUsed);
            env.put(token, id);
            storageUsed += type.width;
        }
    }

    private Type type() {
        Type type = (Type) lookahead; // Expect tag == Tag.BASIC.
        match(Tag.BASIC);
        if (lookahead.tag != '[')
            return type; // Return basic type.
        else
            return arrayType(type); // Return array type.
    }

    private Type arrayType(Type type) {
        match('[');
        Token token = lookahead;
        match(Tag.NUM);
        match(']');
        if (lookahead.tag == '[')
            type = arrayType(type);
        return new Array(type, ((Num) token).value);
    }

    private Statement statements() {
        if (lookahead.tag == '}') {
            return Statement.NULL;
        } else {
            Statement stmt = statement();
            Statement stmts = statements();
            return factory.createStatements(stmt, stmts);
        }
    }

    private Statement statement() {
        Expression expr;
        Statement stmt1, stmt2;
        Statement savedStmt; // Save enclosing loop for breaks.

        switch (lookahead.tag) {
        case ';':
            move();
            return Statement.NULL;
        case Tag.IF:
            move();
            match('(');
            expr = bool();
            match(')');
            stmt1 = statement();
            if (lookahead.tag != Tag.ELSE)
                return factory.createIf(expr, stmt1);
            move();
            stmt2 = statement();
            return factory.createElse(expr, stmt1, stmt2);
        case Tag.WHILE:
            While whileNode = factory.createWhile();
            savedStmt = enclosing;
            enclosing = whileNode;
            move();
            match('(');
            expr = bool();
            match(')');
            stmt1 = statement();
            whileNode.init(expr, stmt1);
            enclosing = savedStmt; // Reset enclosing statement.
            return whileNode;
        case Tag.DO:
            Do doNode = factory.createDo();
            savedStmt = enclosing;
            enclosing = doNode;
            move();
            stmt1 = statement();
            match(Tag.WHILE);
            match('(');
            expr = bool();
            match(')');
            match(';');
            doNode.init(expr, stmt1);
            enclosing = savedStmt; // Reset enclosing statement.
            return doNode;
        case Tag.BREAK:
            move();
            match(';');
            return factory.createBreak(enclosing);
        case '{':
            return block();
        default:
            return assignment();
        }
    }

    private Statement assignment() {
        Statement stmt;
        Token token = lookahead;
        match(Tag.ID);
        Id id = env.get(token);
        if (id == null)
            error(token.toString() + " undeclared");
        if (lookahead.tag == '=') {
            move();
            stmt = factory.createAssignment(id, bool());
        } else {
            ArrayAccess access = arrayAccess(id);
            match('=');
            stmt = factory.createArrayAssignment(access, bool());
        }
        match(';');
        return stmt;
    }

    private Expression bool() {
        Expression expr = join();
        while (lookahead.tag == Tag.OR) {
            Token token = lookahead;
            move();
            expr = factory.createOr(token, expr, join());
        }
        return expr;
    }

    private Expression join() {
        Expression expr = equality();
        while (lookahead.tag == Tag.AND) {
            Token token = lookahead;
            move();
            expr = factory.createAnd(token, expr, equality());
        }
        return expr;
    }

    private Expression equality() {
        Expression expr = relational();
        while (lookahead.tag == Tag.EQUAL || lookahead.tag == Tag.NOT_EQUAL) {
            Token token = lookahead;
            move();
            expr = factory.createRelational(token, expr, relational());
        }
        return expr;
    }

    private Expression relational() {
        Expression expr = expression();
        switch (lookahead.tag) {
        case '<':
        case Tag.LESS_THAN_OR_EQUAL:
        case '>':
        case Tag.GREATER_THAN_OR_EQUAL:
            Token token = lookahead;
            move();
            expr = factory.createRelational(token, expr, expression());
        default:
            return expr;
        }
    }

    private Expression expression() {
        Expression expr = term();
        while (lookahead.tag == '+' || lookahead.tag == '-') {
            Token token = lookahead;
            move();
            expr = factory.createArithmetic(token, expr, term());
        }
        return expr;
    }

    private Expression term() {
        Expression expr = unary();
        while (lookahead.tag == '*' || lookahead.tag == '/') {
            Token token = lookahead;
            move();
            expr = factory.createArithmetic(token, expr, unary());
        }
        return expr;
    }

    private Expression unary() {
        if (lookahead.tag == '+') {
            move();
            return unary();
        } else if (lookahead.tag == '-') {
            move();
            return factory.createUnary(Word.MINUS, unary());
        } else if (lookahead.tag == '!') {
            Token token = lookahead;
            move();
            return factory.createNot(token, unary());
        } else {
            return factor();
        }
    }

    private Expression factor() {
        Expression expr = null;
        switch (lookahead.tag) {
        case '(':
            move();
            expr = bool();
            match(')');
            return expr;
        case Tag.NUM:
            expr = factory.createConstant(lookahead, Type.INT);
            move();
            return expr;
        case Tag.REAL:
            expr = factory.createConstant(lookahead, Type.FLOAT);
            move();
            return expr;
        case Tag.TRUE:
            expr = factory.createConstant(lookahead, Type.BOOL);
            move();
            return expr;
        case Tag.FALSE:
            expr = factory.createConstant(lookahead, Type.BOOL);
            move();
            return expr;
        case Tag.ID:
            Id id = env.get(lookahead);
            if (id == null)
                error(lookahead.toString() + " undeclared");
            move();
            if (lookahead.tag != '[')
                return id;
            else
                return arrayAccess(id);
        default:
            error("syntax error");
            return expr;
        }
    }

    private ArrayAccess arrayAccess(Id array) {
        Expression index, width, temp1, temp2, location;
        Type type = array.type;
        match('[');
        index = bool();
        match(']');
        type = ((Array) type).baseType;
        width = factory.createConstant(type.width);
        temp1 = factory.createArithmetic(new Token('*'), index, width);
        location = temp1;
        // Multidimensional array.
        while (lookahead.tag == '[') {
            move();
            index = bool();
            match(']');
            type = ((Array) type).baseType;
            width = factory.createConstant(type.width);
            temp1 = factory.createArithmetic(new Token('*'), index, width);
            temp2 = factory.createArithmetic(new Token('+'), location, temp1);
            location = temp2;
        }
        return factory.createArrayAccess(array, location, type);
    }
}
