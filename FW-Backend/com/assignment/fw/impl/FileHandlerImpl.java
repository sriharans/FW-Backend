package com.assignment.fw.impl;

import java.io.File;

public class FileHandlerImpl {
    String keyFilepath="";
    String dataFilepath="";
    
    public String createNewFile(String path) {
        String newpath="";
        newpath=path+(path.charAt(path.length()-1)+1);
        return newpath;
        
    }
    
    public static String createDirectory(String directoryPath) {
        if(null==directoryPath) {
            directoryPath="C:\\Users\\z00426FZ\\ds";
        }
        File keyDir = new File(directoryPath+"\\KeyDir");
        File dataDir = new File(directoryPath+"\\DataDir");
        keyDir.mkdirs();
        dataDir.mkdirs();
        return directoryPath;
    }
}
