package assignment.fw.datastore;

import assignment.fw.constants.DataStoreConstant;
import assignment.fw.exception.DataStoreException;
import assignment.fw.model.Stats;
import assignment.fw.service.DataStoreImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

public class DataStore {

    public static String directoryPath;
    public static Stats stats;
    public static int lineNumber = 0;
    private String keyFilepath = "";
    private String dataFilepath = "";

    public DataStore(String directoryPath) {
        stats = new Stats(directoryPath);
        // buildDirectory(directoryPath);
    }

    public DataStore() {
        String directoryPath = "C:\\Users\\z00426FZ\\ds\\";
        stats = new Stats(directoryPath);
        // buildDirectory(directoryPath);

    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDataFilepath(String dataFilepath) {
        this.dataFilepath = dataFilepath;
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
        Object obj = null;
        if (isValidKeyValue(key, jsonObject) && null == getKeyInfo(key)) {
            obj = DataStoreImpl.addData(key, jsonObject, stats);
            Stats.dataFileLine = lineNumber++;
            return obj;
        } else {
            throw new DataStoreException(DataStoreConstant.FAILED_TO_INSERT_DATA_KEY_ALREADY_EXISTS);
        }
    }

    private String getKeyInfo(String key) {
        try {
            Optional<String> line = Files.lines(Paths.get(Stats.currentkeyFile)).filter(l -> l.startsWith(key))
                    .findFirst();
            if (line.isPresent()) {
                return line.get();
            }
        } catch (IOException e) {
            throw new DataStoreException(DataStoreConstant.ERROR_OCCURED_WHILE_RETREIVING_DATA);
        }
        return null;
    }

    public Object insertData(String key, Object jsonObject, long timetoLive) {

        boolean isKeyValueValid = false;
        Object obj = null;

        if (isValidKeyValue(key, jsonObject) && null == getKeyInfo(key)) {
            obj = DataStoreImpl.addData(key, jsonObject, timetoLive, stats);
            Stats.dataFileLine = lineNumber++;
            return obj;
        } else {
            throw new DataStoreException(DataStoreConstant.FAILED_TO_INSERT_DATA_KEY_ALREADY_EXISTS);
        }
    }

    public Object getData(String key) {
        String lineNumber = isKeyExists(key);
        if (null != lineNumber && !lineNumber.isEmpty()) {
            String data = getValue(lineNumber);
            if (null != data && !data.isEmpty()) {
                return DataStoreConstant.DATA_FOUND_WITH_KEY + " " + key + " is " + data;
            }
        }
        return DataStoreConstant.DATA_NOT_FOUND_WITH_KEY + " " + key;
    }

    public String deleteData(String key) {
        if (null != isKeyExists(key) && !isKeyExists(key).isEmpty()) {
            if (null != removeData(key)) {
                return key + " " + DataStoreConstant.DELETED_SUCCESSFULLY + " ";
            }
        }

        return DataStoreConstant.NO_SUCH_KEY + " " + key + " " + DataStoreConstant.EXISTS_TO_DELETE;
    }

    private Path removeData(String key) {
        try {
            Path path = Paths.get(Stats.currentkeyFile);
            Charset charset = StandardCharsets.UTF_8;
            String content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll(key, "NA");
            return Files.write(path, content.getBytes(charset));
        } catch (Exception e) {
            throw new DataStoreException(DataStoreConstant.ERROR_OCCURED_WHILE_DELETING_KEY + " " + key);
        }

    }

    private boolean isValidKeyValue(String key, Object jsonObject) {
        if (DataStoreConstant.KEYLENTH > key.length()) {
            long size = jsonObject.toString().length();
            return DataStoreConstant.VALUEMAXSIZE > (size / 1024);
        }
        return false;

    }

    private String isKeyExists(String key) {

        if (DataStoreConstant.KEYLENTH > key.length()) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(Stats.currentkeyFile));
                String line;
                while ((line = bf.readLine()) != null) {
                    if (line.startsWith(key)) {
                        String[] lineSize = line.split(",");
                        return checkLine(line, lineSize);
                    }

                }
            } catch (IOException e) {
                throw new DataStoreException(DataStoreConstant.ENTERED_KEY_IS_INVALID_PLEASE_CHECK);
            }

        } else {
            throw new DataStoreException(DataStoreConstant.ENTERED_KEY_IS_INVALID_PLEASE_CHECK);
        }
        return null;

    }

    private String checkLine(String line, String[] lineSize) throws DataStoreException {
        if (lineSize.length == 3) {
            return line;
        }
        if (lineSize.length == 4) {
            if (LocalDateTime.parse(lineSize[3]).isAfter(LocalDateTime.now())) {
                return line;
            } else {
                throw new DataStoreException(DataStoreConstant.ENTERED_KEY_IS_INVALID_PLEASE_CHECK);
            }
        }
        return null;
    }

    private String getValue(String line) {
        if (!line.isEmpty()) {
            String[] splited = line.split(",");
            if (2 <= splited.length) {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(splited[1]));
                    while ((line = bf.readLine()) != null) {
                        if (line.startsWith(splited[0]))
                            return line.substring(line.indexOf(":"), line.length());
                    }
                } catch (IOException e) {
                    throw new DataStoreException(DataStoreConstant.ERROR_OCCURED_WHILE_RETREIVING_DATA);
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
            return other.keyFilepath == null;
        } else return keyFilepath.equals(other.keyFilepath);
    }
}
