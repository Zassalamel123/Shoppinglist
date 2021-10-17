package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;


import java.io.FileInputStream;
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
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testItemList.json";
    private final Context mockContext = mock(Context.class);

    @BeforeEach
    public void setUp() throws Exception {
        databaseWriter = new DatabaseWriter(mockContext);
        when(mockContext.openFileOutput(FILE_TEST, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST));
        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        databaseWriter.setJsonFile(FILE_TEST);
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void saveListToJson() throws JSONException {
        databaseWriter.saveListToJson(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveListToJsonMultipleLists() throws JSONException {
        String listName2 = "Kaufland";
        String item3 = "tomato";
        List<String> items2 = new ArrayList<>();
        items2.add(item3);
        databaseWriter.saveListToJson(listName, items);
        databaseWriter.saveListToJson(listName2, items2);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}," + "{" + listName2 + ":{" + item3 + ":false}}" + "]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void setJsonFile() {
        String expectedPath = "src\\test\\java\\ressourcelists\\testItemList.json";
        Assertions.assertEquals(FILE_TEST, expectedPath);
    }
}