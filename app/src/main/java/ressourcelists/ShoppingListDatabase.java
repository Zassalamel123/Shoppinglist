package ressourcelists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class ShoppingListDatabase {

    private JSONArray jsonCollection;
    private String jsonFile = "src/main/java/ressourcelists/ItemList.json";

    public ShoppingListDatabase() {
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

    private void writeJsonToFile(JSONArray jsonArray, String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {

            bufferedWriter.append(jsonArray.toString(4));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String readJsonFile(String filePath) throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

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

    public JSONArray getJsonContent() throws Exception {
        setJsonFile(jsonFile);
        String content = readJsonFile(jsonFile);
        return stringToJson(content);
    }

    public JSONArray getJsonCollection() {
        return jsonCollection;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }
}
