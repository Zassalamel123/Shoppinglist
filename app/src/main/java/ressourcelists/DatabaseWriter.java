package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class DatabaseWriter {

    private JSONArray jsonCollection;
    private String jsonFile = "itemList.json"; //todo change path for android?
    private Context context;

    public DatabaseWriter(Context context) {
        this.context = context;
        jsonCollection = new JSONArray();
    }

    public void saveListToJson(String listName, List<String> items) {
        JSONObject jsonObjectList = new JSONObject();
        JSONObject jsonObjectItems = new JSONObject();
        try {
            for (String item : items) {
                jsonObjectItems.put(item, false);
            }
            jsonObjectList.put(listName, jsonObjectItems);
            jsonCollection.put(jsonObjectList);
            writeJsonToFile(jsonCollection, jsonFile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonToFile(JSONArray jsonArray, String filePath){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE)))){
            String content = jsonArray.toString(4);
            if (content != null) {
                bufferedWriter.write(content);
            }
        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }
    }

    public JSONArray getJsonCollection() {
        return jsonCollection;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
