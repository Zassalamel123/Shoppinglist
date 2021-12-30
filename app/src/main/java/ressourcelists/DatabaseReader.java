package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ressourcelists.Strategies.IndexByJsonCollection;
import ressourcelists.Strategies.JsonIndex;

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
        titleKeys = getTitleKeys();
        return lookUpDuplicateListName(listName);
    }

    private boolean lookUpDuplicateListName(String listName) {
        return titleKeys.contains(listName);
    }

    public List<String> getTitleKeys() throws Exception {
        JSONArray jsonArray = getAllContents();
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

    public JSONArray getAllContents() throws Exception {
        setFilePath(jsonFile);
        String content = readFileIntoString();
        return stringToJson(content);
    }

    private String readFileIntoString() throws Exception {
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

    public void setFilePath(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getFilePath() {
        return jsonFile;
    }

    public boolean doesFileExist() {
        File file = getContext().getFileStreamPath(jsonFile);
        return file.exists();
    }

    public Context getContext() {
        return context;
    }

    public JSONObject getItemsByTitleKey(String titleKey) throws Exception {
        JSONArray content = getAllContents();
        Object specificContent;
        for (int index = 0; index < content.length(); index++) {
            Object entry = content.opt(index);
            JSONObject jsonObject = new JSONObject(entry.toString());
            specificContent = jsonObject.opt(titleKey);
            if (specificContent != null) {
                return new JSONObject(specificContent.toString());
            }
        }
        throw new JSONException("Entry not found");
    }

    public List<String> getItemKeys(String titleKey) throws Exception {
        JSONObject items = getItemsByTitleKey(titleKey);
        Iterator<String> itemKeysIterator = items.keys();
        List<String> itemKeys = new ArrayList<>();
        while (itemKeysIterator.hasNext()) {
            itemKeys.add(itemKeysIterator.next());
        }
        return itemKeys;
    }

    public int getIndexFromJsonCollection(String titleKey, JSONArray content) throws Exception {
        JsonIndex jsonIndex = new JsonIndex(new IndexByJsonCollection());
        return jsonIndex.getIndex(titleKey, content);
    }
}
