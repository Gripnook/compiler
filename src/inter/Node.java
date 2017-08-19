package inter;

import java.io.PrintWriter;

public class Node {
    protected NodeFactory factory;
    protected int lexline;

    private PrintWriter out;

    void setFactory(NodeFactory factory) {
        this.factory = factory;
    }

    void setWriter(PrintWriter out) {
        this.out = out;
    }

    protected Node(int lexline) {
        this.lexline = lexline;
    }

    protected void error(String message) {
        throw new Error("near line " + lexline + ": " + message);
    }

    protected int createLabel() {
        return factory.createLabel();
    }

    protected void emitLabel(int label) {
        out.print("L" + label + ":");
    }

    protected void emit(String s) {
        out.println("\t" + s);
    }
}
