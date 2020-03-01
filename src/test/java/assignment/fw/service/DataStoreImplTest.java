package assignment.fw.service;

import assignment.fw.model.Stats;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DataStoreImplTest {

    @Before
    public void init() {

    }

    @Test
    public void addDataTest() {
        Stats sts = new Stats("C:\\Users\\z00426FZ\\ds\\");
        assertNotNull(DataStoreImpl.addData("Test01", "ValueforTest01", 0, sts));
    }

    @Test
    public void addDatawithExpiryTest() {
        Stats sts = new Stats("C:\\Users\\z00426FZ\\ds\\");
        assertNotNull(DataStoreImpl.addData("Test02", "ValueforTest02", 100, sts));
    }
}
