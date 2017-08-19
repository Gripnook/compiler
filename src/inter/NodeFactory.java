package inter;

import java.io.PrintWriter;
import java.io.Writer;

import lexer.Lexer;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public class NodeFactory {
    private Lexer lex;
    private PrintWriter out;
    private int labels = 0;
    private int tempCount = 0;

    public NodeFactory(Lexer lex, Writer out) {
        this.lex = lex;
        this.out = new PrintWriter(out);
    }

    public int createLabel() {
        return ++labels;
    }

    public Node createNode() {
        Node node = new Node(lex.getLine());
        initialize(node);
        return node;
    }

    public Expr createExpr(Token token, Type type) {
        Expr expr = new Expr(token, type, lex.getLine());
        initialize(expr);
        return expr;
    }

    public Constant createConstant(Token token, Type type) {
        Constant constant = new Constant(token, type, lex.getLine());
        initialize(constant);
        return constant;
    }

    public Constant createConstant(int value) {
        Constant constant = new Constant(value, lex.getLine());
        initialize(constant);
        return constant;
    }

    public Id createId(Word word, Type type, int offset) {
        Id id = new Id(word, type, offset, lex.getLine());
        initialize(id);
        return id;
    }

    public Logical createLogical(Token op, Expr lhs, Expr rhs) {
        Logical logical = new Logical(op, lhs, rhs, lex.getLine());
        initialize(logical);
        return logical;
    }

    public And createAnd(Token op, Expr lhs, Expr rhs) {
        And and = new And(op, lhs, rhs, lex.getLine());
        initialize(and);
        return and;
    }

    public Or createOr(Token op, Expr lhs, Expr rhs) {
        Or or = new Or(op, lhs, rhs, lex.getLine());
        initialize(or);
        return or;
    }

    public Not createNot(Token op, Expr expr) {
        Not not = new Not(op, expr, lex.getLine());
        initialize(not);
        return not;
    }

    public Rel createRel(Token op, Expr lhs, Expr rhs) {
        Rel rel = new Rel(op, lhs, rhs, lex.getLine());
        initialize(rel);
        return rel;
    }

    public Op createOp(Token op, Type type) {
        Op operation = new Op(op, type, lex.getLine());
        initialize(operation);
        return operation;
    }

    public Arith createArith(Token op, Expr lhs, Expr rhs) {
        Arith arith = new Arith(op, lhs, rhs, lex.getLine());
        initialize(arith);
        return arith;
    }

    public Arith createArith(Token op, Expr lhs, Expr rhs, int lexline) {
        Arith arith = new Arith(op, lhs, rhs, lexline);
        initialize(arith);
        return arith;
    }

    public Unary createUnary(Token op, Expr expr) {
        Unary unary = new Unary(op, expr, lex.getLine());
        initialize(unary);
        return unary;
    }

    public Unary createUnary(Token op, Expr expr, int lexline) {
        Unary unary = new Unary(op, expr, lexline);
        initialize(unary);
        return unary;
    }

    public Access createAccess(Id array, Expr index, Type type) {
        Access access = new Access(array, index, type, lex.getLine());
        initialize(access);
        return access;
    }

    public Access createAccess(Id array, Expr index, Type type, int lexline) {
        Access access = new Access(array, index, type, lexline);
        initialize(access);
        return access;
    }

    public Temp createTemp(Type type) {
        Temp temp = new Temp(type, ++tempCount, lex.getLine());
        initialize(temp);
        return temp;
    }

    public Temp createTemp(Type type, int lexline) {
        Temp temp = new Temp(type, ++tempCount, lexline);
        initialize(temp);
        return temp;
    }

    private void initialize(Node node) {
        node.setFactory(this);
        node.setWriter(out);
    }
}
