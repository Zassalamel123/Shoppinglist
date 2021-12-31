package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;

public interface IndexStrategy {

    int getJsonIndex(String key, JSONArray content) throws JSONException;
}
