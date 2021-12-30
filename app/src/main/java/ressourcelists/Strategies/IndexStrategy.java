package ressourcelists.Strategies;

import org.json.JSONException;

public interface IndexStrategy {

    int getJsonIndex(String key, Object jsonObject) throws JSONException;
}
