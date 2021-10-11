package ressourcelists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

public class DatabaseWriter {

    private JSONArray jsonCollection;
    private String jsonFile = "src/main/java/ressourcelists/itemList.json";

    public DatabaseWriter() {
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

    public JSONArray getJsonCollection() {
        return jsonCollection;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

//    private String read(Context context, String fileName) {
//        try {
//            FileInputStream fis = context.openFileInput(fileName);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//            return sb.toString();
//        } catch (FileNotFoundException fileNotFound) {
//            return null;
//        } catch (IOException ioException) {
//            return null;
//        }
//    }
//
//    private boolean create(Context context, String fileName, String jsonString){
//        String FILENAME = "storage.json";
//        try {
//            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
//            if (jsonString != null) {
//                fos.write(jsonString.getBytes());
//            }
//            fos.close();
//            return true;
//        } catch (FileNotFoundException fileNotFound) {
//            return false;
//        } catch (IOException ioException) {
//            return false;
//        }
//
//    }
//
//    public boolean isFilePresent(Context context, String fileName) {
//        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
//        File file = new File(path);
//        return file.exists();
//    }
}
