package managerlists;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import ressourcelists.ShoppingListDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ManagerListTest {

    private ManagerList managerList;

    private ShoppingListDatabase shoppingListDatabase;
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testManagerItemList.json";
    private List<String> items;
    private String item1 = "Ananas";
    private String item2 = "Kiwi";
    private String listName = "Real";

    @BeforeEach
    void setUp() {
        shoppingListDatabase = new ShoppingListDatabase();
        managerList = new ManagerList(shoppingListDatabase);
        shoppingListDatabase.setJsonFile(FILE_TEST);
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
    }

    @Test
    public void getJsonContentTest() throws JSONException {
        shoppingListDatabase.saveToJson(listName, items);

        JSONArray actual = managerList.getJsonContent();

        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";

        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveToJsonTest() throws JSONException {
        managerList.saveToJson(listName, items);

        JSONArray actual = shoppingListDatabase.getJsonCollection();

        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";

        JSONAssert.assertEquals(expected, actual, true);
    }

    @AfterEach
    void tearDown() {
    }
}