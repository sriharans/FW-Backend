package assignment.fw.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Stats {
    public static String currentkeyFile;
    public static int keyFileLine = 1;
    public static String currentdataFile;
    public static int dataFileLine = 0;
    private long keyFileSize;
    private long dataFileSize;

    public Stats(String path) {
        File keyFile = createnewFile(path, "KEY");
        this.keyFileSize = keyFile.length();
        if (keyFile.length() == 0) {
            keyFileLine = 0;
        }
        currentkeyFile = keyFile.getAbsolutePath();
        File dataFile = createnewFile(path, "DATA");
        currentdataFile = createnewFile(path, "DATA").getAbsolutePath();
        this.dataFileSize = dataFile.length();
        if (dataFile.length() == 0) {
            dataFileLine = 0;
        }
    }

    public static File createnewFile(String path, String type) {

        File file = new File(path + "//" + type.toLowerCase());
        if (file.exists() && file.isDirectory()) {
            File lastModifiedFile = getLastModifiedFile(path, type);
            if (null != lastModifiedFile) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(lastModifiedFile));
                    int lines = 0;
                    while (reader.readLine() != null)
                        lines++;
                    if ("key".equals(type.toLowerCase())) {
                        Stats.keyFileLine = lines;
                    } else {
                        Stats.dataFileLine = lines;
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return lastModifiedFile;
            }
        }
        if ("KEY".equals(type)) {
            file = new File(path + "key//key.txt");
        } else {
            file = new File(path + "data//data.txt");
        }
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static File getLastModifiedFile(String path, String type) {
        File directory = new File(path + "//" + type);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;

    }

    public String getCurrentkeyFile() {
        return currentkeyFile;
    }

    public void setCurrentkeyFile(String currentkeyFile) {
        Stats.currentkeyFile = currentkeyFile;
    }

    public long getKeyFileSize() {
        return keyFileSize;
    }

    public void setKeyFileSize(long keyFileSize) {
        this.keyFileSize = keyFileSize;
    }

    public int getKeyFileLine() {
        return keyFileLine;
    }

    public void setKeyFileLine(int keyFileLine) {
        Stats.keyFileLine = keyFileLine;
    }

    public String getCurrentdataFile() {
        return currentdataFile;
    }

    public void setCurrentdataFile(String currentdataFile) {
        Stats.currentdataFile = currentdataFile;
    }

    public long getDataFileSize() {
        return dataFileSize;
    }

    public void setDataFileSize(long dataFileSize) {
        this.dataFileSize = dataFileSize;
    }

    public int getDataFileLine() {
        return dataFileLine;
    }

}
