package ressourcelists;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseReaderTest {

    private DatabaseReader databaseReader;
    private final String item1 = "apple";
    private final String item2 = "banana";
    private final String item3 = "karotten";
    private final String item4 = "erbsen";
    private final String listName1 = "Aldi";
    private final String listName2 = "Real";
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testReaderItemList.json";
    private final Context mockContext = mock(Context.class);

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        databaseReader = new DatabaseReader(mockContext);
        databaseReader.setJsonFilePath(FILE_TEST);

        when(mockContext.openFileInput(FILE_TEST)).thenReturn(new FileInputStream(FILE_TEST));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getKeysFromJsonArray() throws Exception {
        List<String> keys = databaseReader.getKeysFromJsonArray();
        Assertions.assertEquals(listName1, keys.get(0));
        Assertions.assertEquals(listName2, keys.get(1));

    }

    @Test
    public void getJsonContent() throws Exception {
        JSONArray actual = databaseReader.getJsonContent();
        String expected = "[{" + listName1 + ":{" + item1 + ":false," + item2 + ":false}}," +
                " {" + listName2 + ":{ " + item3 + ":false," + item4 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void setJsonFilePath() {
        String testJsonFile = "src\\test\\java\\ressourcelists\\testReaderItemList2.json";
        databaseReader.setJsonFilePath(testJsonFile);
        String expected = testJsonFile;
        String actual = databaseReader.getJsonFilePath();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void doesListNameExist() throws Exception {
        boolean actual = databaseReader.doesListNameExist(listName1);
        Assertions.assertTrue(actual);
    }

    @Test
    public void doesListNameExistReturnFalse() throws Exception {
        boolean actual = databaseReader.doesListNameExist("Media Markt");
        Assertions.assertFalse(actual);
    }

    @Test
    public void getJsonContentFirstEntry() throws Exception {
        JSONArray content = databaseReader.getJsonContent();
        Object firstEntry = content.opt(0);
        String actual = firstEntry.toString();
        String expected = "{" + listName1 + ":{" + item1 + ":false," + item2 + ":false}}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getJsonContentFirstEntryWithoutKey() throws Exception {
        JSONArray content = databaseReader.getJsonContent();
        Object firstEntry = content.opt(0);
        JSONObject jsonObject = new JSONObject(firstEntry.toString());
        Object obj = jsonObject.get(listName1);
        String actual = obj.toString();
        String expected = "{" + item1 + ":false," + item2 + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }
}