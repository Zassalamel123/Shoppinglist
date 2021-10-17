package managerlists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.Iterator;
import java.util.List;

public class ManagerList {

    private DatabaseReader databaseReader;
    private DatabaseWriter databaseWriter;

    public ManagerList(DatabaseReader databaseReader, DatabaseWriter databaseWriter) {
        this.databaseReader = databaseReader;
        this.databaseWriter = databaseWriter;
    }

    public JSONArray getJsonContent() {
        JSONArray content = new JSONArray();
        try {
            content = databaseReader.getJsonContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public Iterator<String> getKeys() {
        Iterator<String> keys = null;
        try {
            keys = databaseReader.getKeysFromJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    public void saveToJson(String listName, List<String> items) {
        databaseWriter.saveListToJson(listName, items);
    }
}
