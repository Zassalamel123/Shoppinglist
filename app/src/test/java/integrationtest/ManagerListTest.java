package integrationtest;

import android.content.Context;
import managerlists.ManagerList;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ManagerListTest {

    private ManagerList managerList;

    private List<String> items;
    private List<String> keys;
    private String listName = "Real";
    private String item1 = "Ananas";
    private String item2 = "Kiwi";
    private final Context mockContext = mock(Context.class);
    private DatabaseReader databaseReader;
    private DatabaseWriter databaseWriter;
    private final String FILE_TEST = "src\\test\\java\\integrationtest\\testIntegrationItemList.json";

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        databaseReader = new DatabaseReader();
        databaseWriter = new DatabaseWriter();
        databaseWriter.setJsonFilePath(FILE_TEST);
        databaseReader.setJsonFilePath(FILE_TEST);
        managerList = new ManagerList(databaseReader, databaseWriter);

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        keys = new ArrayList<>();
        keys.add(listName);

        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST));
//        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_APPEND)).thenReturn(new FileOutputStream(FILE_TEST));
        when(mockContext.openFileInput(FILE_TEST)).thenReturn(new FileInputStream(FILE_TEST));

    }

    @Test
    public void saveMultipleListsTest() throws JSONException, FileNotFoundException {
        managerList.saveToJson(listName, items);

        String listName2 = "Saturn";
        String item3 = "tomato";
        List<String> items2 = new ArrayList<>();
        items2.add(item3);

        managerList.saveToJson(listName2, items2);
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
    }

    //    @Test
//    public void saveToJsonTest() throws JSONException {
//        managerList.saveToJson(listName, items);
//        JSONArray actual = databaseWriter.getJsonCollection();
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }

//    @Test
//    public void getJsonContentTest() throws JSONException {
//        JSONArray actual = managerList.getJsonContent();
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }

//    @Test
//    public void getKeysTest() {
//        List<String> keys = managerList.getKeys();
//        String actual = keys.get(0);
//        String expected = listName;
//        Assertions.assertEquals(expected,actual);
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
}