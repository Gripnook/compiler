package symbols;

import org.junit.*;

public class TestType {
    @Test
    public void testNumeric() {
        Assert.assertTrue(Type.numeric(Type.INT));
        Assert.assertTrue(Type.numeric(Type.FLOAT));
        Assert.assertTrue(Type.numeric(Type.CHAR));
        Assert.assertFalse(Type.numeric(Type.BOOL));
    }

    @Test
    public void testMaxWorksForNumericTypes() {
        Assert.assertEquals(Type.max(Type.FLOAT, Type.FLOAT), Type.FLOAT);
        Assert.assertEquals(Type.max(Type.FLOAT, Type.INT), Type.FLOAT);
        Assert.assertEquals(Type.max(Type.FLOAT, Type.CHAR), Type.FLOAT);
        Assert.assertEquals(Type.max(Type.INT, Type.FLOAT), Type.FLOAT);
        Assert.assertEquals(Type.max(Type.CHAR, Type.FLOAT), Type.FLOAT);
        Assert.assertEquals(Type.max(Type.INT, Type.INT), Type.INT);
        Assert.assertEquals(Type.max(Type.INT, Type.CHAR), Type.INT);
        Assert.assertEquals(Type.max(Type.CHAR, Type.INT), Type.INT);
        Assert.assertEquals(Type.max(Type.CHAR, Type.CHAR), Type.CHAR);
    }

    @Test
    public void testMaxReturnsNullForNonnumericTypes() {
        Assert.assertNull(Type.max(Type.BOOL, Type.FLOAT));
        Assert.assertNull(Type.max(Type.BOOL, Type.INT));
        Assert.assertNull(Type.max(Type.BOOL, Type.CHAR));
        Assert.assertNull(Type.max(Type.FLOAT, Type.BOOL));
        Assert.assertNull(Type.max(Type.INT, Type.BOOL));
        Assert.assertNull(Type.max(Type.CHAR, Type.BOOL));
        Assert.assertNull(Type.max(Type.BOOL, Type.BOOL));

        Assert.assertNull(Type.max(new Array(Type.FLOAT, 10), Type.FLOAT));
        Assert.assertNull(Type.max(new Array(Type.FLOAT, 10), Type.INT));
        Assert.assertNull(Type.max(new Array(Type.FLOAT, 10), Type.CHAR));
        Assert.assertNull(Type.max(Type.FLOAT, new Array(Type.FLOAT, 10)));
        Assert.assertNull(Type.max(Type.INT, new Array(Type.FLOAT, 10)));
        Assert.assertNull(Type.max(Type.CHAR, new Array(Type.FLOAT, 10)));
        Assert.assertNull(Type.max(new Array(Type.FLOAT, 10), new Array(Type.FLOAT, 10)));
    }
}
