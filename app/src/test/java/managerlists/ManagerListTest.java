package managerlists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ManagerListTest {

    private ManagerList managerList;

    private List<String> items;
    private List<String> keys;
    private String listName = "Real";
    private String item1 = "Ananas";
    private String item2 = "Kiwi";
    private final DatabaseWriter mockDatabaseWriter = mock(DatabaseWriter.class);
    private final DatabaseReader mockDatabaseReader = mock(DatabaseReader.class);
    private JSONArray jsonContent;

    @BeforeEach
    public void setUp() throws Exception {
        managerList = new ManagerList(mockDatabaseReader, mockDatabaseWriter);
        jsonContent = new JSONArray("[{\"" + listName + "\": {\n" +
                "    \"" + item1 + "\": false,\n" +
                "    \"" + item2 + "\": false\n" +
                "}}]");
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        keys = new ArrayList<>();
        keys.add(listName);

        when(mockDatabaseReader.getAllContents()).thenReturn(jsonContent);
        when(mockDatabaseWriter.getJsonCollection()).thenReturn(jsonContent);
    }

    @Test
    public void getJsonContent() throws JSONException {
        JSONArray actual = managerList.getAllContents();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getKeys() throws Exception {
        when(mockDatabaseReader.getTitleKeys()).thenReturn(keys);

        List<String> keys = managerList.getTitleKeys();
        String actual = keys.get(0);
        String expected = listName;
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void saveToJson() throws JSONException {
        managerList.saveItemList(listName, items);
        JSONArray actual = mockDatabaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getJsonContentByKey() throws Exception {
        JSONObject specificContent = new JSONObject("{" +
                "    \"" + item1 + "\": false,\n" +
                "    \"" + item2 + "\": false\n" +
                "}");
        when(mockDatabaseReader.getItemsByTitleKey(listName)).thenReturn(specificContent);

        JSONObject actual = managerList.getItemsByTitleKey(listName);
        String expected = "{" + item1 + ":false," + item2 + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getItemKeys() throws Exception {
        List<String> itemKeys = new ArrayList<>();
        itemKeys.add(item1);
        itemKeys.add(item2);
        when(mockDatabaseReader.getItemKeys(listName)).thenReturn(itemKeys);

        itemKeys = managerList.getItemKeys(listName);
        String actual = itemKeys.get(0);
        String expected = item1;
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void updateItemValue() throws Exception {
        int index = 0;
        JSONObject specificContent = new JSONObject("{" +
                "    \"" + item1 + "\": true,\n" +
                "    \"" + item2 + "\": false\n" +
                "}");
        when(mockDatabaseReader.getIndexFromJsonCollection(listName)).thenReturn(index);
        when(mockDatabaseReader.getItemsByTitleKey(listName)).thenReturn(specificContent);

        managerList.updateItemValue(listName, item1, true);
        JSONObject actual = managerList.getItemsByTitleKey(listName);
        String expected = "{" + item1 + ":true," + item2 + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getItemValue() throws Exception {
        JSONObject specificContent = new JSONObject("{" +
                "    \"" + item1 + "\": true,\n" +
                "    \"" + item2 + "\": false\n" +
                "}");
        when(mockDatabaseReader.getItemsByTitleKey(listName)).thenReturn(specificContent);

        boolean actual = (boolean) managerList.getItemValue(listName, item1);
        boolean expected = true;
        Assertions.assertEquals(expected,actual);
    }

    @AfterEach
    public void tearDown() {
    }
}