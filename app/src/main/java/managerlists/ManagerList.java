package managerlists;

import org.json.JSONArray;
import ressourcelists.ShoppingListDatabase;

import java.util.List;

public class ManagerList {

    private ShoppingListDatabase shoppingListDatabase;

    public ManagerList(ShoppingListDatabase shoppingListDatabase) {
        this.shoppingListDatabase = shoppingListDatabase;
    }

    public JSONArray getJsonContent() {
        JSONArray content = new JSONArray();
        try {
            content = shoppingListDatabase.getJsonContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public void saveToJson(String listName, List<String> items) {
        shoppingListDatabase.saveListToJson(listName, items);
    }
}
