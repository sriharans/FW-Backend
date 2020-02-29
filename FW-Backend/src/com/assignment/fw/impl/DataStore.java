package com.assignment.fw.impl;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import assignment.fw.model.Stats;
import assignment.fw.service.DataStoreImpl;

public class DataStore {
    private String keyFilepath = "";
    private String dataFilepath = "";
    public static String directoryPath;
    public static Stats stats;
    public int lineNumber = 0;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        directoryPath = directoryPath;
    }

    public String getKeyFilepath() {
        return keyFilepath;
    }

    public void setKeyFilepath(String keyFilepath) {
        this.keyFilepath = keyFilepath;
    }

    public String getDataFilepath() {
        return dataFilepath;
    }

    public void setDataFilepath(String dataFilepath) {
        this.dataFilepath = dataFilepath;
    }

    public String createNewFile(String path) {
        String newpath = "";
        newpath = path + (path.charAt(path.length() - 1) + 1);
        return newpath;

    }

    public DataStore(String directoryPath) {
        this.stats = new Stats(directoryPath);
        //buildDirectory(directoryPath);
    }

    public DataStore() {
        String directoryPath = "C:\\Users\\z00426FZ\\ds\\";
        this.stats = new Stats(directoryPath);
        // buildDirectory(directoryPath);

    }

    private void buildDirectory(String directoryPath) {
        DataStore.directoryPath = directoryPath;
        File keyDir = new File(directoryPath + "\\KeyDir");
        File dataDir = new File(directoryPath + "\\DataDir");
        keyDir.mkdirs();
        dataDir.mkdirs();
        this.keyFilepath = keyDir.getAbsolutePath();
        this.dataFilepath = dataDir.getAbsolutePath();
    }

    public Object insertData(String key, Object jsonObject) {
        boolean isKeyValueValid = false;
        Object obj = null;
        //
        if (isValidKeyValue(key, jsonObject) && null == getKeyInfo(key)) {
            obj = DataStoreImpl.addData(key, jsonObject, stats);
            Stats.dataFileLine = lineNumber++;
            return obj;
        } else {
            throw new RuntimeException("Failed to insert Data. Key already exists");
        }

    }

    private String getKeyInfo(String key) {
        try {
            Optional<String> line = Files.lines(Paths.get(Stats.currentkeyFile)).filter(l -> l.startsWith(key)).findFirst();
            if (line.isPresent()) {
                return line.get().toString();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Object insertData(String key, Object jsonObject, long timetoLive) {
       
        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonObject = objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }*/
        return DataStoreImpl.addData(key, jsonObject, timetoLive, stats);
    }

    public Object getData(String key) {
        String lineNumber = isKeyExists(key);
        if (null != lineNumber && !lineNumber.isEmpty()) {
            String data = getValue(lineNumber);
            if (!data.isEmpty()) {
                return "Data found with Key " + key + " is " + data;
            }

        }
        return "Data Not found with Key " + key;
    }

    public String deleteData(String key) {
       return "deleted success";
    }



    private boolean isValidKeyValue(String key, Object jsonObject) {
        if(33>key.length()) {
            long size=jsonObject.toString().length();
            if(16>(size/1024)) {
                return true;
            }
        }
        return false;
        
    }

    private String  isKeyExists(String key) {

        if(33>key.length()) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(Stats.currentkeyFile));
                String line;
                while ((line = bf.readLine()) != null) {
                    if(line.startsWith(key))
                        return line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            throw new RuntimeException("Entered Key is Invalid.. Please check");
        }
        return null;

    }

    private String getValue(String line){
        if(!line.isEmpty()) {
            String[] splited = line.split(",");
            if (3 == splited.length) {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(splited[1]));
                    String data;
                    while ((line = bf.readLine()) != null) {
                        if (line.startsWith(splited[0]))
                            return line.substring(line.indexOf(":"), line.length() - 1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    @Override
    public String toString() {
        return "DataStore [keyFilepath=" + keyFilepath + ", dataFilepath=" + dataFilepath + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataFilepath == null) ? 0 : dataFilepath.hashCode());
        result = prime * result + ((keyFilepath == null) ? 0 : keyFilepath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataStore other = (DataStore) obj;
        if (dataFilepath == null) {
            if (other.dataFilepath != null)
                return false;
        } else if (!dataFilepath.equals(other.dataFilepath))
            return false;
        if (keyFilepath == null) {
            if (other.keyFilepath != null)
                return false;
        } else if (!keyFilepath.equals(other.keyFilepath))
            return false;
        return true;
    }
}
