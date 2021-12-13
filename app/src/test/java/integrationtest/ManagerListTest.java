package integrationtest;

import android.content.Context;
import managerlists.ManagerList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONAssert;
import ressourcelists.DatabaseReader;
import ressourcelists.DatabaseWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
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
    private String listName = "Saturn";
    private String item1 = "tomato";
    private String item2 = "Kiwi";
    private Context mockContext = mock(Context.class);
    private DatabaseReader databaseReader;
    private DatabaseWriter databaseWriter;
    private final String FILE_TEST_SAVE_SINGLE_LIST = "src/test/java/integrationtest/testSaveSingleList.json";
    private final String FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE = "src/test/java/integrationtest/testSaveMultipleListsBefore.json";
    private final String FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER = "src/test/java/integrationtest/testSaveMultipleListsAfter.json";
    private final String FILE_TEST_READ_ONLY = "src/test/java/integrationtest/testReadOnlyItems.json";
    private final String FILE_TEST_UPDATE_ITEM_VALUE_BEFORE = "src/test/java/integrationtest/testUpdateItemValueBefore.json";
    private final String FILE_TEST_UPDATE_ITEM_VALUE_AFTER = "src/test/java/integrationtest/testUpdateItemValueAfter.json";

    @BeforeEach
    public void setUp() {
        databaseReader = new DatabaseReader(mockContext);
        databaseWriter = new DatabaseWriter(mockContext);
        managerList = new ManagerList(databaseReader, databaseWriter);

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        keys = new ArrayList<>();
        keys.add(listName);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void saveItemList() throws JSONException, FileNotFoundException {
        databaseWriter.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        databaseReader.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        when(mockContext.openFileOutput(FILE_TEST_SAVE_SINGLE_LIST, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_SAVE_SINGLE_LIST));
        when(mockContext.openFileInput(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new FileInputStream(FILE_TEST_SAVE_SINGLE_LIST));
        when(mockContext.getFileStreamPath(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new File(FILE_TEST_SAVE_SINGLE_LIST));

        managerList.saveItemList(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveMultipleLists() throws JSONException, FileNotFoundException {
        databaseWriter.setFilePath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE);
        databaseReader.setFilePath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE);
        when(mockContext.openFileOutput(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER));
        when(mockContext.openFileInput(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE)).thenReturn(new FileInputStream(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE));
        when(mockContext.getFileStreamPath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE)).thenReturn(new File(FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER));

        managerList.saveItemList(listName, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + "Aldi" + ":{" + "Plume" + ":false," + "Banana" + ":false}}," +
                "{ " + listName + ":{ " + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getAllContents() throws JSONException, FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        when(mockContext.openFileInput(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new FileInputStream(FILE_TEST_SAVE_SINGLE_LIST));

        JSONArray actual = managerList.getAllContents();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getTitleKeys() throws FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        when(mockContext.openFileInput(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new FileInputStream(FILE_TEST_SAVE_SINGLE_LIST));

        List<String> keys = managerList.getTitleKeys();
        String actual = keys.get(0);
        String expected = listName;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getItemsByTitleKey() throws FileNotFoundException, JSONException {
        databaseReader.setFilePath(FILE_TEST_READ_ONLY);
        when(mockContext.openFileInput(FILE_TEST_READ_ONLY)).thenReturn(new FileInputStream(FILE_TEST_READ_ONLY));

        JSONObject actual = managerList.getItemsByTitleKey(listName);
        String expected = "{" + item1 + ":true," + item2 + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getItemValue() throws FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_READ_ONLY);
        when(mockContext.openFileInput(FILE_TEST_READ_ONLY)).thenReturn(new FileInputStream(FILE_TEST_READ_ONLY));

        Object actual = managerList.getItemValue(listName, item1);
        Assertions.assertTrue((Boolean) actual);
    }

    @Test
    public void updateItemValue() throws FileNotFoundException, JSONException {
        databaseWriter.setFilePath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE);
        databaseReader.setFilePath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE);
        when(mockContext.openFileOutput(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_UPDATE_ITEM_VALUE_AFTER));
        when(mockContext.openFileInput(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE)).thenReturn(new FileInputStream(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE));
        when(mockContext.getFileStreamPath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE)).thenReturn(new File(FILE_TEST_UPDATE_ITEM_VALUE_AFTER));

        managerList.updateItemValue(listName, item2, true);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":true}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }
}