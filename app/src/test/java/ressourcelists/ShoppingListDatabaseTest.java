package ressourcelists;

import org.json.JSONArray;
import org.json.JSONException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;


import java.util.ArrayList;
import java.util.List;


public class ShoppingListDatabaseTest {

    private ShoppingListDatabase shoppingListDatabase;
    private String item1 = "apple";
    private String item2 = "banana";
    private List<String> items;
    private String listName = "Aldi";
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testItemList.json";

    @BeforeEach
    public void setUp() throws Exception {
        shoppingListDatabase = new ShoppingListDatabase();
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        shoppingListDatabase.setJsonFile(FILE_TEST);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void saveToJsonTest() throws JSONException {

        shoppingListDatabase.saveToJson(listName, items);

        JSONArray actual = shoppingListDatabase.getJsonCollection();

        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";

        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveToJsonMultipleListsTest() throws JSONException {

        String listName2 = "Kaufland";
        String item3 = "tomato";
        List<String> items2 = new ArrayList<>();
        items2.add(item3);

        shoppingListDatabase.saveToJson(listName, items);
        shoppingListDatabase.saveToJson(listName2, items2);

        JSONArray actual = shoppingListDatabase.getJsonCollection();

        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}," + "{" + listName2 + ":{" + item3 + ":false}}" + "]";

        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getJsonContentTest() throws Exception {
        String expectedPath = "src\\test\\java\\ressourcelists\\testItemList.json";
        Assertions.assertEquals(FILE_TEST, expectedPath);

        shoppingListDatabase.setJsonFile("src\\test\\java\\ressourcelists\\testItemList2.json");

        shoppingListDatabase.saveToJson(listName, items);

        JSONArray actual = shoppingListDatabase.getJsonContent();

        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";

        JSONAssert.assertEquals(expected, actual, true);
    }
}