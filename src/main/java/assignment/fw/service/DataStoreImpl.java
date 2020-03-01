package assignment.fw.service;

import assignment.fw.constants.DataStoreConstant;
import assignment.fw.datastore.DataStore;
import assignment.fw.exception.DataStoreException;
import assignment.fw.model.Stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class DataStoreImpl {


    public static Object addData(String key, Object obj, Stats stats) {
        return addJsonObject(key, obj, 0, stats);
    }

    public static Object addData(String key, Object obj, long timetoLive, Stats stats) {
        return addJsonObject(key, obj, timetoLive, stats);
    }

    private static Object addJsonObject(String key, Object obj, long timetoLive, Stats stats) {
        String keyValue = "";
        if (0 != timetoLive) {
            keyValue = key + "," + stats.getCurrentdataFile() + "," + (DataStore.lineNumber) + "," + LocalDateTime.now().plusSeconds(timetoLive);
        } else {
            keyValue = key + "," + stats.getCurrentdataFile() + "," + (DataStore.lineNumber);
        }
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(stats.getCurrentdataFile(), true));
            bw.write(key + "@Value:" + obj.toString() + System.lineSeparator());
            bw.close();
            stats.setKeyFileLine(stats.getDataFileLine() + 1);
            bw = new BufferedWriter(new FileWriter(stats.getCurrentkeyFile(), true));
            bw.write(keyValue + System.lineSeparator());
            bw.close();
            stats.setKeyFileLine(stats.getKeyFileLine() + 1);

        } catch (IOException e1) {
            throw new DataStoreException(DataStoreConstant.UNEXPECTED_EXCEPTION_OCCURED_WHILE_INSERTION);
        }
        return obj;
    }


}
