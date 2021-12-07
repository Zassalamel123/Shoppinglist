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
    private String item1 = "apple";
    private String item2 = "banana";
    private String item3 = "karotten";
    private String item4 = "erbsen";
    private String listName1 = "Aldi";
    private String listName2 = "Real";
    private String FILE_TEST = "src\\test\\java\\ressourcelists\\testReaderItemList.json";
    private Context mockContext = mock(Context.class);

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        databaseReader = new DatabaseReader(mockContext);
        databaseReader.setFilePath(FILE_TEST);

        when(mockContext.openFileInput(FILE_TEST)).thenReturn(new FileInputStream(FILE_TEST));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getTitleKeys() throws Exception {
        List<String> keys = databaseReader.getTitleKeys();
        Assertions.assertEquals(listName1, keys.get(0));
        Assertions.assertEquals(listName2, keys.get(1));
    }

    @Test
    public void getAllContents() throws Exception {
        JSONArray actual = databaseReader.getAllContents();
        String expected = "[{" + listName1 + ":{" + item1 + ":false," + item2 + ":false}}," +
                " {" + listName2 + ":{ " + item3 + ":false," + item4 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void setFilePath() {
        String testJsonFile = "src\\test\\java\\ressourcelists\\testReaderItemList2.json";

        databaseReader.setFilePath(testJsonFile);
        String expected = testJsonFile;
        String actual = databaseReader.getFilePath();
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
    public void getItemContentsByTitleKey() throws Exception {
        JSONObject firstEntryContent = databaseReader.getItemsByTitleKey(listName1);
        String actual = firstEntryContent.toString();
        String expected = "{" + item1 + ":false," + item2 + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getItemKeys() throws Exception {
        List<String> itemKeys = databaseReader.getItemKeys(listName1);
        Assertions.assertEquals(item1, itemKeys.get(1));
        Assertions.assertEquals(item2, itemKeys.get(0));
    }

    @Test
    public void getIndexFromJsonCollection() throws Exception {
        int expected = 1;
        int actual = databaseReader.getIndexFromJsonCollection(listName2);
        Assertions.assertEquals(expected, actual);
    }
}