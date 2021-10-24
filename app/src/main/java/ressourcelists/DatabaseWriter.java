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
//    private DatabaseReader databaseReader;
    public DatabaseWriter(Context context) {
//        databaseReader = new DatabaseReader(context);
        this.context = context;
        jsonCollection = new JSONArray();
    }

    public void setJsonCollection(JSONArray jsonCollection) {
        this.jsonCollection = jsonCollection;
    }

    public void saveListToJson(String listName, List<String> items) throws Exception {
        JSONObject jsonObjectList = new JSONObject();
        JSONObject jsonObjectItems = new JSONObject();
        try {
            for (String item : items) {
                jsonObjectItems.put(item, false);
            }
            jsonObjectList.put(listName, jsonObjectItems);
            jsonCollection.put(jsonObjectList);
            //todo test if append works correctly
            if (new File(jsonFile).exists()) {
                appendJsonArrayToFile(jsonCollection, jsonFile);
//                appendJsonObjectToFile(jsonObjectList, jsonFile);
            } else {
                writeJsonArrayToFile(jsonCollection, jsonFile);
//                writeJsonObjectToFile(jsonObjectList, jsonFile);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonArrayToFile(JSONArray jsonArray, String filePath) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE)))){
            String content = jsonArray.toString(4);
            if (content != null) {
                bufferedWriter.write(content);
            }
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    private void writeJsonObjectToFile(JSONObject jsonObject, String filePath) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE)))){
            String content = jsonObject.toString(4);
            bufferedWriter.write(content);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    private void appendJsonArrayToFile(JSONArray jsonArray, String filePath) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_APPEND)))){
            String content = jsonArray.toString(4);
            bufferedWriter.append(content);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    private void appendJsonObjectToFile(JSONObject jsonObject, String filePath) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filePath, Context.MODE_APPEND)))){
            String content = jsonObject.toString(4);
            bufferedWriter.append(content);
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    public JSONArray getJsonCollection() {
        return jsonCollection;
    }

    public void setJsonFilePath(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getJsonFilePath() {
        return jsonFile;
    }
}
