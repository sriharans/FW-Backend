package assignment.fw.model;

public class KeyMeta {
    private String Key;
    private long expiryTime;
    private String fileName;
    private int lineNumber;

    public KeyMeta(String key, String fileName, int lineNumber, long expiryTime) {
        Key = key;
        this.expiryTime = expiryTime;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public KeyMeta(String key, String fileName, int lineNumber) {
        Key = key;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public KeyMeta() {

    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "KeyMeta [Key=" + Key + ", expiryTime=" + expiryTime + ", fileName=" + fileName + ", lineNumber="
                + lineNumber + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Key == null) ? 0 : Key.hashCode());
        result = prime * result + (int) (expiryTime ^ (expiryTime >>> 32));
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + lineNumber;
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
        KeyMeta other = (KeyMeta) obj;
        if (Key == null) {
            if (other.Key != null)
                return false;
        } else if (!Key.equals(other.Key))
            return false;
        if (expiryTime != other.expiryTime)
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        return lineNumber == other.lineNumber;
    }


}
