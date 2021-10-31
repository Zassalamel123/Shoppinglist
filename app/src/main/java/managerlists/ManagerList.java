package managerlists;

import org.json.JSONArray;
import org.json.JSONObject;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

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

    public JSONObject getJsonContentByKey(String key) {
        JSONObject content = null;
        try {
            content = databaseReader.getJsonContentByTitleKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public List<String> getKeys() {
        List<String> keys = null;
        try {
            keys = databaseReader.getKeysFromJsonArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    public void saveToJson(String listName, List<String> items) {
        try {
            if (databaseReader.doesFileExist()) {
                JSONArray jsonArray = databaseReader.getJsonContent();
                databaseWriter.setJsonCollection(jsonArray);
            }
            databaseWriter.saveListToJson(listName, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //todo overrite titlelist for duplicate?
}
