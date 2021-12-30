package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexByJsonCollection implements IndexStrategy {

    @Override
    public int getJsonIndex(String key, Object jsonObject) throws JSONException {
        JSONArray content = (JSONArray) jsonObject;
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
