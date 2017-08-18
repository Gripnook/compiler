package symbols;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestEnv {
    @Test
    public void testStoresSymbols() {
        Env global = new Env(null);

        global.put("x", new MockSymbol("global"));
        global.put("y", new MockSymbol("global"));

        Assert.assertNotNull(global.get("x"));
        Assert.assertNotNull(global.get("y"));
    }

    @Test
    public void testChildScopeCanAccessParentScope() {
        Env global = new Env(null);
        Env local = new Env(global);

        global.put("x", new MockSymbol("global"));

        Assert.assertNotNull(local.get("x"));
        Assert.assertEquals(((MockSymbol) local.get("x")).scope, "global");
    }

    @Test
    public void testChildScopeOverridesParentScope() {
        Env global = new Env(null);
        Env local = new Env(global);

        global.put("x", new MockSymbol("global"));
        local.put("x", new MockSymbol("local"));

        Assert.assertNotNull(local.get("x"));
        Assert.assertEquals(((MockSymbol) local.get("x")).scope, "local");
    }

    @Test
    public void testMissingDeclarationReturnsNull() {
        Env global = new Env(null);
        Env local = new Env(global);

        Assert.assertNull(local.get("x"));
    }
}
