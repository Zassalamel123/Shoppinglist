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
    private List<String> titleKeys;

    public DatabaseReader(Context context) {
        this.context = context;
        titleKeys = new ArrayList<>();
    }

    public boolean doesListNameExist(String listName) throws Exception {
        titleKeys = getTitleKeysFromJsonArray();
        return lookUpDuplicateListName(listName);
    }

    private boolean lookUpDuplicateListName(String listName) {
        return titleKeys.contains(listName);
    }

    public List<String> getTitleKeysFromJsonArray() throws Exception {
        JSONArray jsonArray = getJsonContent();
        return extractJsonArrayKeys(jsonArray);
    }

    private List<String> extractJsonArrayKeys(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.opt(i);
            Iterator<String> iterator = jsonObject.keys();
            titleKeys.add(iterator.next());
        }
        return titleKeys;
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

    public JSONObject getJsonContentByTitleKey(String key) throws Exception {
        JSONArray content = getJsonContent();
        Object specificContent;
        for (int index = 0; index < content.length(); index++) {
            Object entry = content.opt(index);
            JSONObject jsonObject = new JSONObject(entry.toString());
            specificContent = jsonObject.opt(key);
            if (specificContent != null) {
                return new JSONObject(specificContent.toString());
            }
        }
        throw new JSONException("Entry not found");
    }

    public List<String> getItemKeys(String titleKey) throws Exception {
        JSONObject items = getJsonContentByTitleKey(titleKey);
        Iterator<String> itemKeysIterator = items.keys();
        List<String> itemKeys = new ArrayList<>();
        while (itemKeysIterator.hasNext()) {
            itemKeys.add(itemKeysIterator.next());
        }
        return itemKeys;
    }
}
