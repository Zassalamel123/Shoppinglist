package ressourcelists;

import org.json.JSONArray;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class DatabaseReaderTest {

    private DatabaseReader databaseReader;
    private final String item1 = "apple";
    private final String item2 = "banana";
    private final String listName = "Aldi";
    private final String FILE_TEST = "src\\test\\java\\ressourcelists\\testItemList2.json";

    @BeforeEach
    void setUp() {
        databaseReader = new DatabaseReader();
        List<String> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        databaseReader.setJsonFile(FILE_TEST);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getKeysFromJsonArray() throws Exception {
        databaseReader.setJsonFile("src\\test\\java\\ressourcelists\\testItemList2.json");
        Iterator<String> jsonKeys = databaseReader.getKeysFromJsonArray();
        String actual = "";
        while (jsonKeys.hasNext()) {
            actual = jsonKeys.next();
        }
        Assertions.assertEquals(listName, actual);
    }

    @Test
    void getJsonContent() throws Exception {
        databaseReader.setJsonFile("src\\test\\java\\ressourcelists\\testItemList2.json");
        JSONArray actual = databaseReader.getJsonContent();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    void setJsonFile() {
        String expectedPath = "src\\test\\java\\ressourcelists\\testItemList2.json";
        Assertions.assertEquals(FILE_TEST, expectedPath);
    }
}