package symbols;

import java.util.HashMap;
import java.util.Map;

public class Env {
    private Map<String, Symbol> table;
    protected Env prev;

    public Env(Env prev) {
        table = new HashMap<>();
        this.prev = prev;
    }

    public void put(String key, Symbol value) {
        table.put(key, value);
    }

    public Symbol get(String key) {
        for (Env env = this; env != null; env = env.prev) {
            Symbol value = env.table.get(key);
            if (value != null)
                return value;
        }
        return null;
    }
}
