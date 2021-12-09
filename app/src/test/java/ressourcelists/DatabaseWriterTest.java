package ressourcelists;

import android.content.Context;
import org.json.JSONArray;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.FileOutputStream;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatabaseWriterTest {

    private DatabaseWriter databaseWriter;

    private String item1 = "apple";
    private String item2 = "banana";
    private List<String> items;
    private String listName = "Aldi";
    private String FILE_TEST = "src\\test\\java\\ressourcelists\\testWriterItemList.json";
    private String FILE_TEST2 = "src\\test\\java\\ressourcelists\\testWriterItemList2.json";
    private String FILE_TEST3 = "src\\test\\java\\ressourcelists\\testWriterItemList3.json";
    private String FILE_TEST_DELETE_BEFORE = "src\\test\\java\\ressourcelists\\testWriterItemListDeleteBefore.json";
    private String FILE_TEST_DELETE_AFTER = "src\\test\\java\\ressourcelists\\testWriterItemListDeleteAfter.json";

    private Context mockContext = mock(Context.class);

    private FileOutputStream fileOutputStream;

    @BeforeEach
    public void setUp() throws Exception {
        databaseWriter = new DatabaseWriter(mockContext);
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void saveItemList() throws Exception {
        databaseWriter.setFilePath(FILE_TEST);
        fileOutputStream = new FileOutputStream(FILE_TEST);
        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_PRIVATE)).thenReturn(fileOutputStream);

        databaseWriter.saveItemList(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveItemList2() throws Exception {
        databaseWriter.setFilePath(FILE_TEST);
        fileOutputStream = new FileOutputStream(FILE_TEST);
        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_PRIVATE)).thenReturn(fileOutputStream);

        databaseWriter.saveItemList(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }


    @Test
    public void setFilePath() {
        String testJsonFile = "src\\test\\java\\ressourcelists\\testPathWriterItemList.json";

        databaseWriter.setFilePath(testJsonFile);
        String expected = testJsonFile;
        String actual = databaseWriter.getFilePath();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateItemValue() throws Exception {
        databaseWriter.setFilePath(FILE_TEST2);
        fileOutputStream = new FileOutputStream(FILE_TEST3);
        when(mockContext.openFileOutput(FILE_TEST2, Context.MODE_PRIVATE)).thenReturn(fileOutputStream);

        JSONArray mockedJsonArray = new JSONArray("[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]");
        databaseWriter.setJsonCollection(mockedJsonArray);
        JSONObject mockedItemsContent = new JSONObject("{" +
                item2 + ": false," +
                item1 + ": true" +
                "}");

        databaseWriter.updateItemValue(listName, mockedItemsContent, 0);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":true," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void deleteList() throws Exception {
        databaseWriter.setFilePath(FILE_TEST_DELETE_BEFORE);
        when(mockContext.openFileOutput(FILE_TEST_DELETE_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_DELETE_AFTER));

        databaseWriter.deleteList(0);
        String expected = "[]";
        JSONArray actual = databaseWriter.getJsonCollection();
        JSONAssert.assertEquals(expected, actual, true);
    }
}