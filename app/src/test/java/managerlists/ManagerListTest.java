//package managerlists;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import ressourcelists.DatabaseWriter;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//
//class ManagerListTest {
//
//    private ManagerList managerList;
//
//    private DatabaseWriter databaseWriter;
//    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testManagerItemList.json";
//    private List<String> items;
//    private String item1 = "Ananas";
//    private String item2 = "Kiwi";
//    private String listName = "Real";
//
//    @BeforeEach
//    void setUp() {
//        databaseWriter = new DatabaseWriter();
//        managerList = new ManagerList(databaseWriter);
//        databaseWriter.setJsonFile(FILE_TEST);
//        items = new ArrayList<>();
//        items.add(item1);
//        items.add(item2);
//    }
//
//    @Test
//    public void getJsonContentTest() throws JSONException {
//        databaseWriter.saveListToJson(listName, items);
//        JSONArray actual = managerList.getJsonContent();
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }
//
//    @Test
//    public void getKeysTest() throws JSONException {
//        managerList.saveToJson(listName, items);
//        Iterator<String> keys = managerList.getKeys();
//        String actual = keys.next();
//        String expected = listName;
//        Assertions.assertEquals(expected,actual);
//    }
//
//    @Test
//    public void saveToJsonTest() throws JSONException {
//        managerList.saveToJson(listName, items);
//        JSONArray actual = databaseWriter.getJsonCollection();
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//}