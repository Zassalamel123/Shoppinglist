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

    public Iterator<String> getKeysFromJsonArray() throws Exception {
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
//            JSONObject jsnobject = new JSONObject(fileContent);
//            JSONArray jsonArray = jsnobject.getJSONArray("Titel");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject explrObject = jsonArray.getJSONObject(i);
//            }
//            return jsonArray;
            return new JSONArray(fileContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        throw new Exception("Cannot convert String to Json");
    }
}
