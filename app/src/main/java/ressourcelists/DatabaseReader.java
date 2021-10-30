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
    private List<String> keys;

    public DatabaseReader(Context context) {
        this.context = context;
        keys = new ArrayList<>();
    }

    public boolean doesListNameExist(String listName) throws Exception {
        keys = getKeysFromJsonArray();
        return lookUpDuplicateListName(listName);
    }

    private boolean lookUpDuplicateListName(String listName) {
        return keys.contains(listName);
    }

    public List<String> getKeysFromJsonArray() throws Exception {
        JSONArray jsonArray = getJsonContent();
        return extractJsonArrayKeys(jsonArray);
    }

    private List<String> extractJsonArrayKeys(JSONArray jsonArray) {
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
        } catch (IOException exception) {
            throw new IOException(exception);
        }
    }

    private JSONArray stringToJson(String fileContent) throws Exception {
        try {
            if (fileContent.equals("")) {
                return new JSONArray();
            }
            return new JSONArray(fileContent);
        } catch (JSONException exception) {
            throw new JSONException("Cannot convert String to Json");
        }
    }

    public String getJsonFilePath() {
        return jsonFile;
    }

    public boolean doesFileExist() {
        File file = new File(jsonFile);
        if (file.exists()) {
            return true;
        }
        return false;
    }
}
