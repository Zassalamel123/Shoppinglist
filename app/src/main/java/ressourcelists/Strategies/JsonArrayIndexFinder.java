package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;

public class JsonArrayIndexFinder {

    private IndexStrategy strategy;

    public JsonArrayIndexFinder(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public int getIndex(String key, JSONArray content) throws JSONException {
        return strategy.getJsonIndex(key, content);
    }
}
