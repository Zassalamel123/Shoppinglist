package ressourcelists;

import android.content.Context;
import org.json.JSONArray;

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
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testWriterItemList.json";

    private final Context mockContext = mock(Context.class);

    @BeforeEach
    public void setUp() throws Exception {
        databaseWriter = new DatabaseWriter(mockContext);
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        databaseWriter.setJsonFilePath(FILE_TEST);

        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST));
        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_APPEND)).thenReturn(new FileOutputStream(FILE_TEST));
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void saveListToJson() throws Exception {
        databaseWriter.saveListToJson(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void setJsonFilePath() {
        String testJsonFile = "src\\test\\java\\ressourcelists\\testWriterItemList2.json";
        databaseWriter.setJsonFilePath(testJsonFile);
        String expected = testJsonFile;
        String actual = databaseWriter.getJsonFilePath();
        Assertions.assertEquals(expected, actual);
    }
}