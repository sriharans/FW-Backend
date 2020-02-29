package assignment.fw.model;

import java.io.*;

public class Stats {
    public static String currentkeyFile;
    private long keyFileSize;
    public static int keyFileLine = 1;
    public static String currentdataFile;
    private long dataFileSize;
    public static int dataFileLine = 0;

    public String getCurrentkeyFile() {
        return currentkeyFile;
    }

    public void setCurrentkeyFile(String currentkeyFile) {
        this.currentkeyFile = currentkeyFile;
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
        this.keyFileLine = keyFileLine;
    }

    public String getCurrentdataFile() {
        return currentdataFile;
    }

    public void setCurrentdataFile(String currentdataFile) {
        this.currentdataFile = currentdataFile;
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



    public Stats(String path) {
        File keyFile = createnewFile(path, "KEY");
        this.keyFileSize = keyFile.length();
        if (keyFile.length() == 0) {
            this.keyFileLine = 0;
        }
        this.currentkeyFile = keyFile.getAbsolutePath();
        File dataFile = createnewFile(path, "DATA");
        this.currentdataFile = createnewFile(path, "DATA").getAbsolutePath();
        this.dataFileSize = dataFile.length();
        if (dataFile.length() == 0) {
            this.dataFileLine = 0;
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

}
