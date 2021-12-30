package ressourcelists.Strategies;

import org.json.JSONException;

public class JsonIndex {

    private IndexStrategy strategy;

    public JsonIndex(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public int getIndex(String key, Object jsonObject) throws JSONException {
        return strategy.getJsonIndex(key, jsonObject);
    }
}
