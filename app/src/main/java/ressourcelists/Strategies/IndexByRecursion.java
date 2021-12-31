package ressourcelists.Strategies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexByRecursion implements IndexStrategy {

    private int counter = 1;

    @Override
    public int getJsonIndex(String key, JSONArray content) throws JSONException {
        int index = content.length() - counter;
        Object entry = content.opt(index);

        if (entry == null) {
            throw new JSONException("Entry not found");
        }

        JSONObject specificContent = new JSONObject(entry.toString());

        if (specificContent.has(key)) {
            return index;
        } else {
            counter++;
            return getJsonIndex(key, content);
        }
    }
}
