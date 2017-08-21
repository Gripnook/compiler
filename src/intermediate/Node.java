package intermediate;

public abstract class Node {
    protected IntermediateCodeGenerator generator = null;
    protected NodeFactory factory = null;
    protected int lexline = 0;

    protected Node(int lexline) {
        this.lexline = lexline;
    }

    void setGenerator(IntermediateCodeGenerator generator) {
        this.generator = generator;
    }

    void setFactory(NodeFactory factory) {
        this.factory = factory;
    }

    protected void error(String message) {
        throw new Error("near line " + lexline + ": " + message);
    }
}
