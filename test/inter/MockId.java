package inter;

public class MockId extends Id {
    public final String scope;

    public MockId(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof MockId) && scope.equals(((MockId) other).scope);
    }
}
