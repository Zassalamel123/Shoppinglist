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
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

class ManagerListTest {

    private ManagerList managerList;

    private List<String> items;
    private String listName = "Real";
    private String item1 = "Ananas";
    private String item2 = "Kiwi";
    private final DatabaseWriter mockDatabaseWriter = mock(DatabaseWriter.class);
    private final DatabaseReader mockDatabaseReader = mock(DatabaseReader.class);
    private JSONArray jsonContent;

    private JSONObject jsonObject;

    @BeforeEach
    void setUp() throws Exception {
        managerList = new ManagerList(mockDatabaseReader, mockDatabaseWriter);
        jsonContent = new JSONArray("[{\"" + listName + "\": {\n" +
                "    \"" + item1 + "\": false,\n" +
                "    \"" + item2 + "\": false\n" +
                "}}]");
        jsonObject = (JSONObject) jsonContent.opt(0);
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        when(mockDatabaseReader.getJsonContent()).thenReturn(jsonContent);
        when(mockDatabaseWriter.getJsonCollection()).thenReturn(jsonContent);
        when(mockDatabaseReader.getKeysFromJsonArray()).thenReturn(jsonObject.keys());
        doNothing().when(mockDatabaseWriter).saveListToJson(listName, items);
    }

    @Test
    public void getJsonContentTest() throws JSONException {
        JSONArray actual = managerList.getJsonContent();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getKeysTest() throws JSONException {
        Iterator<String> keys = managerList.getKeys();
        String actual = keys.next();
        String expected = listName;
        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void saveToJsonTest() throws JSONException {
        managerList.saveToJson(listName, items);
        JSONArray actual = mockDatabaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @AfterEach
    void tearDown() {
    }
}