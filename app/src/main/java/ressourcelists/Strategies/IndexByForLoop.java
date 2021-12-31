package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexByForLoop implements IndexStrategy {

    @Override
    public int getJsonIndex(String key, JSONArray content) throws JSONException {
        for (int index = 0; index < content.length(); index++) {
            Object entry = content.opt(index);
            JSONObject specificContent = new JSONObject(entry.toString());
            if (specificContent.has(key)) {
                return index;
            }
        }
        throw new JSONException("Entry not found");
    }
}
