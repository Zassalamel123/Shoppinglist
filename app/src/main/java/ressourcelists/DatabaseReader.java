package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseReader {

    private String jsonFile = "itemList.json";
    private Context context;

    public DatabaseReader(Context context) {
        this.context = context;
    }

    public boolean doesListNameExist(String listName) throws Exception {
        List<String> keys = getKeysFromJsonArray();
        return lookUpDuplicateListName(keys, listName);
    }

    private boolean lookUpDuplicateListName(List<String> keys, String listName) {
        return keys.contains(listName);
    }

    public List<String> getKeysFromJsonArray() throws Exception {
        JSONArray jsonArray = getJsonContent();
        return getJsonObjectKeys(jsonArray);
    }

    private List<String> getJsonObjectKeys(JSONArray jsonArray) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
            Iterator<String> iterator = jsonObject.keys();
            keys.add(iterator.next());
        }
        return keys;
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
