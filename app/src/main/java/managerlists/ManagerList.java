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

    public JSONObject getItemsByTitleKey(String key) {
        JSONObject items = null;
        try {
            items = databaseReader.getItemsByTitleKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public Object getItemValue(String key, String item) {
        JSONObject items;
        Object itemValue = null;
        try {
            items = databaseReader.getItemsByTitleKey(key);
            if (items.has(item)) {
                itemValue = items.get(item);
            } else
                throw new Exception("Item not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemValue;
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
            itemContents = databaseReader.getItemsByTitleKey(titleKey);
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

    private boolean doesFileExist() {
        return databaseReader.doesFileExist();
    }

    private void setJsonCollectionForWriting() throws Exception {
        JSONArray jsonArray = databaseReader.getAllContents();
        databaseWriter.setJsonCollection(jsonArray);
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

    public void deleteList(String titleKey) {
        int index;
        try {
            setJsonCollectionForWriting();
            index = databaseReader.getIndexFromJsonCollection(titleKey);
            databaseWriter.deleteList(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean doesListNameExist(String title) {
        try {
            return databaseReader.doesListNameExist(title);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
