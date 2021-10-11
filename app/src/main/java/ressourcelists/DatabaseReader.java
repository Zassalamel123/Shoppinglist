package ressourcelists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class DatabaseReader {

    private String jsonFile = "src/main/java/ressourcelists/itemList.json";

    public Iterator<String> getKeysFromJsonArray() throws Exception{
        JSONArray jsonArray = getJsonContent();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = (JSONObject) jsonArray.opt(i);
        }
        return jsonObject.keys();
    }

    public JSONArray getJsonContent() throws Exception {
        setJsonFile(jsonFile);
        String content = readJsonFile(jsonFile);
        return stringToJson(content);
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
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
}
