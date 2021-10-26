package managerlists;

import org.json.JSONArray;
import org.json.JSONException;
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

        when(mockDatabaseReader.getJsonContent()).thenReturn(jsonContent);
        when(mockDatabaseWriter.getJsonCollection()).thenReturn(jsonContent);
        when(mockDatabaseReader.getKeysFromJsonArray()).thenReturn(keys);
    }

    @Test
    public void getJsonContentTest() throws JSONException {
        JSONArray actual = managerList.getJsonContent();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getKeysTest() {
        List<String> keys = managerList.getKeys();
        String actual = keys.get(0);
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
    public void tearDown() {
    }
}