package intermediate;

import lexer.Lexer;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public class NodeFactory {
    private Lexer lex;
    private IntermediateCodeGenerator generator;
    private int tempCount = 0;

    public NodeFactory(Lexer lex, IntermediateCodeGenerator generator) {
        this.lex = lex;
        this.generator = generator;
    }

    public Constant createConstant(Token token, Type type) {
        Constant constant = new Constant(token, type, lex.getLineNumber());
        initialize(constant);
        return constant;
    }

    public Constant createConstant(Token token, Type type, int lexline) {
        Constant constant = new Constant(token, type, lexline);
        initialize(constant);
        return constant;
    }

    public Constant createConstant(int value) {
        Constant constant = new Constant(value, lex.getLineNumber());
        initialize(constant);
        return constant;
    }

    public Id createId(Word word, Type type, int offset) {
        Id id = new Id(word, type, offset, lex.getLineNumber());
        initialize(id);
        return id;
    }

    public And createAnd(Token op, Expression lhs, Expression rhs) {
        And and = new And(op, lhs, rhs, lex.getLineNumber());
        initialize(and);
        return and;
    }

    public Or createOr(Token op, Expression lhs, Expression rhs) {
        Or or = new Or(op, lhs, rhs, lex.getLineNumber());
        initialize(or);
        return or;
    }

    public Not createNot(Token op, Expression expr) {
        Not not = new Not(op, expr, lex.getLineNumber());
        initialize(not);
        return not;
    }

    public Relational createRelational(Token op, Expression lhs, Expression rhs) {
        Relational relational = new Relational(op, lhs, rhs, lex.getLineNumber());
        initialize(relational);
        return relational;
    }

    public Arithmetic createArithmetic(Token op, Expression lhs, Expression rhs) {
        Arithmetic arithmetic = new Arithmetic(op, lhs, rhs, lex.getLineNumber());
        initialize(arithmetic);
        return arithmetic;
    }

    public Arithmetic createArithmetic(Token op, Expression lhs, Expression rhs, int lexline) {
        Arithmetic arithmetic = new Arithmetic(op, lhs, rhs, lexline);
        initialize(arithmetic);
        return arithmetic;
    }

    public Unary createUnary(Token op, Expression expr) {
        Unary unary = new Unary(op, expr, lex.getLineNumber());
        initialize(unary);
        return unary;
    }

    public Unary createUnary(Token op, Expression expr, int lexline) {
        Unary unary = new Unary(op, expr, lexline);
        initialize(unary);
        return unary;
    }

    public ArrayAccess createArrayAccess(Id array, Expression index, Type type) {
        ArrayAccess access = new ArrayAccess(array, index, type, lex.getLineNumber());
        initialize(access);
        return access;
    }

    public ArrayAccess createArrayAccess(Id array, Expression index, Type type, int lexline) {
        ArrayAccess access = new ArrayAccess(array, index, type, lexline);
        initialize(access);
        return access;
    }

    public Temp createTemp(Type type) {
        Temp temp = new Temp(type, ++tempCount, lex.getLineNumber());
        initialize(temp);
        return temp;
    }

    public Temp createTemp(Type type, int lexline) {
        Temp temp = new Temp(type, ++tempCount, lexline);
        initialize(temp);
        return temp;
    }

    public If createIf(Expression expr, Statement stmt) {
        If statement = new If(expr, stmt, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public Else createElse(Expression expr, Statement stmt1, Statement stmt2) {
        Else statement = new Else(expr, stmt1, stmt2, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public While createWhile() {
        While statement = new While(lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public Do createDo() {
        Do statement = new Do(lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public Break createBreak(Statement enclosing) {
        Break statement = new Break(enclosing, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public Statements createStatements(Statement stmt1, Statement stmt2) {
        Statements statement = new Statements(stmt1, stmt2, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public Assignment createAssignment(Id id, Expression expr) {
        Assignment statement = new Assignment(id, expr, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    public ArrayAssignment createArrayAssignment(ArrayAccess access, Expression expr) {
        ArrayAssignment statement = new ArrayAssignment(access, expr, lex.getLineNumber());
        initialize(statement);
        return statement;
    }

    private void initialize(Node node) {
        node.setGenerator(generator);
        node.setFactory(this);
    }
}
