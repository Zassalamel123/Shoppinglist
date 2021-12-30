package ressourcelists.Strategies;

import org.json.JSONException;

public class IndexByJsonObject implements IndexStrategy{

    @Override
    public int getJsonIndex(String key, Object jsonObject) throws JSONException {
        return 0;
    }
}
