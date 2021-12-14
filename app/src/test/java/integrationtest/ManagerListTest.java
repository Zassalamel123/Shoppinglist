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
    private final String LIST_NAME_SATURN = "Saturn";
    private final String ITEM_TOMATO = "tomato";
    private final String ITEM_KIWI = "Kiwi";
    private final String LIST_NAME_ALDI = "Aldi";
    private final String ITEM_PLUME = "Plume";
    private final String ITEM_BANANA = "Banana";
    private Context mockContext = mock(Context.class);
    private DatabaseReader databaseReader;
    private DatabaseWriter databaseWriter;
    private final String FILE_TEST_SAVE_SINGLE_LIST = "src/test/java/integrationtest/testSaveSingleList.json";
    private final String FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE = "src/test/java/integrationtest/testSaveMultipleListsBefore.json";
    private final String FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER = "src/test/java/integrationtest/testSaveMultipleListsAfter.json";
    private final String FILE_TEST_READ_ONLY = "src/test/java/integrationtest/testReadOnlyItems.json";
    private final String FILE_TEST_UPDATE_ITEM_VALUE_BEFORE = "src/test/java/integrationtest/testUpdateItemValueBefore.json";
    private final String FILE_TEST_UPDATE_ITEM_VALUE_AFTER = "src/test/java/integrationtest/testUpdateItemValueAfter.json";
    private final String FILE_TEST_DELETE_LIST_BEFORE = "src/test/java/integrationtest/testDeleteListBefore.json";
    private final String FILE_TEST_DELETE_LIST_AFTER = "src/test/java/integrationtest/testDeleteListAfter.json";

    @BeforeEach
    public void setUp() {
        databaseReader = new DatabaseReader(mockContext);
        databaseWriter = new DatabaseWriter(mockContext);
        managerList = new ManagerList(databaseReader, databaseWriter);

        items = new ArrayList<>();
        items.add(ITEM_TOMATO);
        items.add(ITEM_KIWI);

        keys = new ArrayList<>();
        keys.add(LIST_NAME_SATURN);
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

        managerList.saveItemList(LIST_NAME_SATURN, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + LIST_NAME_SATURN + ":{" + ITEM_TOMATO + ":false," + ITEM_KIWI + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void saveMultipleLists() throws JSONException, FileNotFoundException {
        databaseWriter.setFilePath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE);
        databaseReader.setFilePath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE);
        when(mockContext.openFileOutput(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER));
        when(mockContext.openFileInput(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE)).thenReturn(new FileInputStream(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE));
        when(mockContext.getFileStreamPath(FILE_TEST_SAVE_MULTIPLE_LISTS_BEFORE)).thenReturn(new File(FILE_TEST_SAVE_MULTIPLE_LISTS_AFTER));

        managerList.saveItemList(LIST_NAME_SATURN, items);
        JSONArray actual = databaseWriter.getJsonCollection();
        String expected = "[{" + LIST_NAME_ALDI + ":{" + ITEM_PLUME + ":false," + ITEM_BANANA + ":false}}," +
                "{ " + LIST_NAME_SATURN + ":{ " + ITEM_TOMATO + ":false," + ITEM_KIWI + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getAllContents() throws JSONException, FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        when(mockContext.openFileInput(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new FileInputStream(FILE_TEST_SAVE_SINGLE_LIST));

        JSONArray actual = managerList.getAllContents();
        String expected = "[{" + LIST_NAME_SATURN + ":{" + ITEM_TOMATO + ":false," + ITEM_KIWI + ":false}}]";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getTitleKeys() throws FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_SAVE_SINGLE_LIST);
        when(mockContext.openFileInput(FILE_TEST_SAVE_SINGLE_LIST)).thenReturn(new FileInputStream(FILE_TEST_SAVE_SINGLE_LIST));

        List<String> keys = managerList.getTitleKeys();
        String actual = keys.get(0);
        String expected = LIST_NAME_SATURN;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getItemsByTitleKey() throws FileNotFoundException, JSONException {
        databaseReader.setFilePath(FILE_TEST_READ_ONLY);
        when(mockContext.openFileInput(FILE_TEST_READ_ONLY)).thenReturn(new FileInputStream(FILE_TEST_READ_ONLY));

        JSONObject actual = managerList.getItemsByTitleKey(LIST_NAME_SATURN);
        String expected = "{" + ITEM_TOMATO + ":true," + ITEM_KIWI + ":false}";
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void getItemValue() throws FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_READ_ONLY);
        when(mockContext.openFileInput(FILE_TEST_READ_ONLY)).thenReturn(new FileInputStream(FILE_TEST_READ_ONLY));

        Object actual = managerList.getItemValue(LIST_NAME_SATURN, ITEM_TOMATO);
        Assertions.assertTrue((Boolean) actual);
    }

    //TODO test is not working, because stream is closed
//    @Test
//    public void updateItemValue() throws FileNotFoundException, JSONException {
//        databaseWriter.setFilePath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE);
//        databaseReader.setFilePath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE);
//        when(mockContext.openFileOutput(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_UPDATE_ITEM_VALUE_AFTER));
//        when(mockContext.openFileInput(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE)).thenReturn(new FileInputStream(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE));
//        when(mockContext.getFileStreamPath(FILE_TEST_UPDATE_ITEM_VALUE_BEFORE)).thenReturn(new File(FILE_TEST_UPDATE_ITEM_VALUE_AFTER));
//
//        managerList.updateItemValue(listName, item2, true);
//        JSONArray actual = databaseWriter.getJsonCollection();
//        String expected = "[{" + listName + ":{" + item1 + ":false," + item2 + ":true}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }

    @Test
    public void getItemKeys() throws FileNotFoundException {
        databaseReader.setFilePath(FILE_TEST_READ_ONLY);
        when(mockContext.openFileInput(FILE_TEST_READ_ONLY)).thenReturn(new FileInputStream(FILE_TEST_READ_ONLY));

        List<String> itemKeys = managerList.getItemKeys(LIST_NAME_SATURN);
        String actual = itemKeys.get(0);
        String expected = ITEM_KIWI;
        Assertions.assertEquals(expected, actual);
    }

    //TODO test is not working, because stream is closed
//    @Test
//    public void deleteList() throws FileNotFoundException, JSONException {
//        databaseWriter.setFilePath(FILE_TEST_DELETE_LIST_BEFORE);
//        databaseReader.setFilePath(FILE_TEST_DELETE_LIST_BEFORE);
//        when(mockContext.openFileOutput(FILE_TEST_DELETE_LIST_BEFORE, Context.MODE_PRIVATE)).thenReturn(new FileOutputStream(FILE_TEST_DELETE_LIST_AFTER));
//        when(mockContext.openFileInput(FILE_TEST_DELETE_LIST_BEFORE)).thenReturn(new FileInputStream(FILE_TEST_DELETE_LIST_BEFORE));
//        when(mockContext.getFileStreamPath(FILE_TEST_DELETE_LIST_BEFORE)).thenReturn(new File(FILE_TEST_DELETE_LIST_AFTER));
//
//        managerList.deleteList(LIST_NAME_SATURN);
//        JSONArray actual = databaseWriter.getJsonCollection();
//        String expected = "[{" + LIST_NAME_ALDI + ":{" + ITEM_PLUME + ":false," + ITEM_BANANA + ":false}}]";
//        JSONAssert.assertEquals(expected, actual, true);
//    }
}