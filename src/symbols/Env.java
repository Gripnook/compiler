package symbols;

import java.util.HashMap;
import java.util.Map;

import inter.Id;
import lexer.Token;

public class Env {
    private Map<Token, Id> table;
    protected Env prev;

    public Env(Env prev) {
        table = new HashMap<>();
        this.prev = prev;
    }

    public void put(Token key, Id value) {
        table.put(key, value);
    }

    public Id get(Token key) {
        for (Env env = this; env != null; env = env.prev) {
            Id value = env.table.get(key);
            if (value != null)
                return value;
        }
        return null;
    }
}
