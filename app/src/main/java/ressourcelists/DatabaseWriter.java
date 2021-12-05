package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class DatabaseWriter {

    private JSONArray jsonCollection;

    private String jsonFile = "itemList.json";
    private Context context;

    public DatabaseWriter(Context context) {
        this.context = context;
        jsonCollection = new JSONArray();
    }

    public void saveItemList(String listName, List<String> items) throws Exception {
        JSONObject jsonTitleWithItems = new JSONObject();
        JSONObject jsonItems = new JSONObject();
        try {
            for (String item : items) {
                jsonItems.put(item, false);
            }
            jsonTitleWithItems.put(listName, jsonItems);
            jsonCollection.put(jsonTitleWithItems);
            writeJsonCollectionToFile(jsonCollection, jsonFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonCollectionToFile(JSONArray jsonArray, String filePath) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE)))) {
            String content = jsonArray.toString(4);
            if (content != null) {
                bufferedWriter.write(content);
            }
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    public void updateItemValue(String titleKey, JSONObject itemContents, int index) throws Exception {
        JSONArray collection = getJsonCollection();
        Object entry = collection.opt(index);
        JSONObject titleWithItemsObject = new JSONObject(entry.toString());
        titleWithItemsObject.put(titleKey, itemContents);

        jsonCollection.remove(index);
        jsonCollection.put(titleWithItemsObject);
        writeJsonCollectionToFile(jsonCollection, jsonFile);
    }

    public void deleteList(int index) throws Exception {
        jsonCollection.remove(index);
        writeJsonCollectionToFile(jsonCollection, jsonFile);
    }

    public JSONArray getJsonCollection() {
        return jsonCollection;
    }

    public void setJsonCollection(JSONArray jsonCollection) {
        this.jsonCollection = jsonCollection;
    }

    public void setFilePath(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getFilePath() {
        return jsonFile;
    }
}
