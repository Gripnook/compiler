package lexer;

import java.io.StringReader;
import java.util.regex.Pattern;

import org.junit.*;

public class TestScanner {
    private Scanner in;

    @After
    public void tearDown() {
        in.close();
    }

    @Test
    public void testCanCreateWithReader() {
        StringReader buffer = new StringReader("");
        in = new Scanner(buffer);
    }

    @Test
    public void testCanCreateWithInputStream() {
        in = new Scanner(System.in);
    }

    @Test
    public void testScanFindsPattern() {
        StringReader buffer = new StringReader("42");
        in = new Scanner(buffer);

        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "42");
    }

    @Test
    public void testScanFindsFirstMatchOfPattern() {
        StringReader buffer = new StringReader("100 42 88");
        in = new Scanner(buffer);

        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "100");
    }

    @Test
    public void testScanAdvancesBufferAfterMatch() {
        StringReader buffer = new StringReader("100 42 88");
        in = new Scanner(buffer);

        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "100");
        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "42");
        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "88");
    }

    @Test
    public void testScanIgnoresAllInputBeforeTheMatchingPattern() {
        StringReader buffer = new StringReader("xyz42");
        in = new Scanner(buffer);

        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "42");
    }

    @Test
    public void testScanReturnsNullWhenPatternCannotBeFound() {
        StringReader buffer = new StringReader("xyz");
        in = new Scanner(buffer);

        Assert.assertNull(in.scan(Pattern.compile("\\d+")));
    }

    @Test
    public void testScanActsOnOneLineAtATime() {
        StringReader buffer = new StringReader("xyz\n42");
        in = new Scanner(buffer);

        Assert.assertNull(in.scan(Pattern.compile("\\d+")));
        Assert.assertEquals(in.scan(Pattern.compile("\\w+")), "xyz");
        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "42");
    }

    @Test
    public void testScanCountsTheLinesScanned() {
        StringReader buffer = new StringReader("\n\n\n\n42");
        in = new Scanner(buffer);

        Assert.assertEquals(in.scan(Pattern.compile("\\d+")), "42");
        Assert.assertEquals(in.getLineNumber(), 5);
    }

    @Test
    public void testScanReturnsNullIfNoMoreInputIsAvailable() {
        StringReader buffer = new StringReader("");
        in = new Scanner(buffer);

        Assert.assertNull(in.scan(Pattern.compile("\\d+")));
    }
}
