package assignment.fw.datastore;

import assignment.fw.exception.DataStoreException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DataStoreTest {

    @Before
    public void init() {

    }

    @Test
    public void insertDataTest() {
        DataStore ds = new DataStore();
        assertNotNull(ds.insertData("Test03", "ValueforTest03"));
    }

    @Test
    public void insertDatawithExpiryTest() {
        DataStore ds = new DataStore("C:\\Users\\z00426FZ\\ds\\");
        assertNotNull(ds.insertData("Test04", "ValueforTest03", 100));
    }

    @Test(expected = DataStoreException.class)
    public void insertDataExceptionTest() {
        DataStore ds = new DataStore("C:\\Users\\z00426FZ\\ds\\");
        ds.insertData("Test04", "ValueforTest03", 100);
    }

    @Test
    public void getDataTest() {
        DataStore ds = new DataStore("C:\\Users\\z00426FZ\\ds\\");
        assertNotNull(ds.getData("Test03"));
    }

    @Test
    public void getDeleteTest() {
        DataStore ds = new DataStore("C:\\Users\\z00426FZ\\ds\\");
        assertNotNull(ds.getData("Test03"));
    }
}
