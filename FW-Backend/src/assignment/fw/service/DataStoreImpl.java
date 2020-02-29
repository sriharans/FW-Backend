package assignment.fw.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import assignment.fw.model.Stats;

public class DataStoreImpl {


    public static Object addData(String key, Object obj, Stats stats) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(stats.getCurrentdataFile(), true));
            bw.write(key + "@Value:" + obj.toString() + System.lineSeparator());
            bw.close();
            bw = new BufferedWriter(new FileWriter(stats.getCurrentkeyFile(), true));
            String keyValue = key + "," + stats.getCurrentdataFile() + "," + stats.getDataFileLine();
            bw.write(keyValue + System.lineSeparator());
            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return obj;

    }

    public static Object addData(String key, Object obj, long timetoLive, Stats stats) {
        String keyValue = key + "," + stats.getCurrentdataFile() + "," + stats.getKeyFileLine()+","+timetoLive;
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
            e1.printStackTrace();
        }
        return obj;
    }




}
