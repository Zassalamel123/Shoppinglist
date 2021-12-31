package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonArrayIndex {

    private IndexStrategy strategy;

    public JsonArrayIndex(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public int getIndex(String key, JSONArray content) throws JSONException {
        return strategy.getJsonIndex(key, content);
    }
}
