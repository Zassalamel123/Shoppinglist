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

    public JSONArray getAllContents() {
        JSONArray content = new JSONArray();
        try {
            content = databaseReader.getAllContents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public JSONObject getItemContentsByTitleKey(String key) {
        JSONObject content = null;
        try {
            content = databaseReader.getItemContentsByTitleKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public List<String> getTitleKeys() {
        List<String> keys = null;
        try {
            keys = databaseReader.getTitleKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keys;
    }

    public void saveItemList(String listName, List<String> items) {
        try {
            setCollectionIfFileExists();
            databaseWriter.saveItemList(listName, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItemValue(String titleKey, String itemKey, boolean itemValue) {
        int index;
        JSONObject itemContents = null;
        try {
            setCollectionIfFileExists();
            index = databaseReader.getIndexFromJsonCollection(titleKey);
            itemContents = databaseReader.getItemContentsByTitleKey(titleKey);
            itemContents.put(itemKey, itemValue);
            databaseWriter.updateItemValue(titleKey, itemContents, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCollectionIfFileExists() throws Exception {
        if (doesFileExist()) {
            setJsonCollectionForWriting();
        }
    }

    private void setJsonCollectionForWriting() throws Exception {
        JSONArray jsonArray = databaseReader.getAllContents();
        databaseWriter.setJsonCollection(jsonArray);
    }

    private boolean doesFileExist() {
        return databaseReader.doesFileExist();
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
