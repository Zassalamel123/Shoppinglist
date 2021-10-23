package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Iterator;

public class DatabaseReader {

    private String jsonFile = "itemList.json";
    private Context context;

    public DatabaseReader(Context context) {
        this.context = context;
    }

    public boolean doesListNameExist(String listName) throws Exception {
        Iterator<String> iterator = getKeysFromJsonArray();
        return lookUpDuplicateListName(iterator, listName);
    }

    private boolean lookUpDuplicateListName(Iterator<String> iterator, String listName) {
        while (iterator.hasNext()) {
            if (iterator.next().equals(listName)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<String> getKeysFromJsonArray() throws Exception {
        JSONArray jsonArray = getJsonContent();
        return getJsonObjectKeys(jsonArray);
    }

    private Iterator<String> getJsonObjectKeys(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = (JSONObject) jsonArray.opt(i);
        }
        return jsonObject.keys();
    }

    public JSONArray getJsonContent() throws Exception {
        setJsonFilePath(jsonFile);
        String content = readJsonFile();
        return stringToJson(content);
    }

    public void setJsonFilePath(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    private String readJsonFile() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.openFileInput(jsonFile)))) {
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new Exception("Cannot read Json File");
    }

    private JSONArray stringToJson(String fileContent) throws Exception {
        try {
            return new JSONArray(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new Exception("Cannot convert String to Json");
    }

    public String getJsonFilePath() {
        return jsonFile;
    }
}
