package lexer;

import java.io.InputStream;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner implements AutoCloseable {
    private java.util.Scanner in;
    private String line = "";
    private int lineNumber = 0;

    public Scanner(Reader in) {
        this.in = new java.util.Scanner(in);
    }

    public Scanner(InputStream in) {
        this.in = new java.util.Scanner(in);
    }

    @Override
    public void close() {
        in.close();
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String scan(Pattern pattern) {
        while (line.isEmpty()) {
            if (!in.hasNextLine())
                return null;
            line = in.nextLine();
            ++lineNumber;
        }

        Matcher matcher = pattern.matcher(line);
        if (!matcher.find())
            return null;
        String match = matcher.group();
        line = line.substring(matcher.end());
        return match;
    }
}
