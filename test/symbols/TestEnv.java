package symbols;

import org.testng.Assert;
import org.testng.annotations.Test;

import intermediate.MockId;
import lexer.Tag;
import lexer.Word;

public class TestEnv {
    private static final Word X = new Word(Tag.ID, "x");
    private static final Word Y = new Word(Tag.ID, "y");

    private static final MockId GLOBAL = new MockId("global");
    private static final MockId LOCAL = new MockId("local");

    @Test
    public void testStoresIds() {
        Env global = new Env(null);

        global.put(X, GLOBAL);
        global.put(Y, GLOBAL);

        Assert.assertEquals(global.get(X), GLOBAL);
        Assert.assertEquals(global.get(Y), GLOBAL);
    }

    @Test
    public void testChildScopeCanAccessParentScope() {
        Env global = new Env(null);
        Env local = new Env(global);

        global.put(X, GLOBAL);

        Assert.assertEquals(local.get(X), GLOBAL);
    }

    @Test
    public void testChildScopeOverridesParentScope() {
        Env global = new Env(null);
        Env local = new Env(global);

        global.put(X, GLOBAL);
        local.put(X, LOCAL);

        Assert.assertEquals(local.get(X), LOCAL);
    }

    @Test
    public void testMissingDeclarationReturnsNull() {
        Env global = new Env(null);
        Env local = new Env(global);

        Assert.assertNull(local.get(X));
    }
}
