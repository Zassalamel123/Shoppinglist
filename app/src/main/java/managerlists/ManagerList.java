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

    public List<String> getTitleKeys() {
        List<String> keys = null;
        try {
            keys = databaseReader.getTitleKeysFromJsonArray();
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

    public List<String> getItemKeys(String titleKey) {
        List<String> itemKeys = null;
        try {
            itemKeys = databaseReader.getItemKeys(titleKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemKeys;
    }

    //todo overrite titlelist for duplicate?
}
